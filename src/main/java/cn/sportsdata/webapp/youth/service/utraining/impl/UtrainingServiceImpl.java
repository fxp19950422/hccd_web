package cn.sportsdata.webapp.youth.service.utraining.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLink;
import cn.sportsdata.webapp.youth.common.bo.UtrainingBO;
import cn.sportsdata.webapp.youth.common.bo.UtrainingTaskEvaBO;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.MatchTaskVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.TrainingExtVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.TrainingMatchVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingVO;
import cn.sportsdata.webapp.youth.dao.user.UserDAO;
import cn.sportsdata.webapp.youth.dao.utraining.UtrainingDAO;
import cn.sportsdata.webapp.youth.service.utraining.UtrainingService;
import cn.sportsdata.webapp.youth.service.utraining.task.ITaskDao;
import cn.sportsdata.webapp.youth.service.utraining.task.TaskDaoFact;
import cn.sportsdata.webapp.youth.web.controller.utraining.UtrainingController;

@Service
public class UtrainingServiceImpl implements UtrainingService {

	@Autowired
	private UtrainingDAO utrainingDao;

	@Autowired
	private UserDAO userDao;
	
	@Override
	public List<String> getUtrainingYears(String orgId) {
		return utrainingDao.selectUtrainingYears(orgId);
	}

	@Override
	@Transactional
	public String addNewUtraining(UtrainingBO bo) {
		String id = "";
		UtrainingVO vo = new UtrainingVO();
		BeanUtils.copyProperties(bo, vo);
		vo.setTrainingPlayIdList(bo.getTrainingPlayIdList());
		id = utrainingDao.addUtraining(vo);
		if (vo.getTrainingPlayIdList() != null && vo.getTrainingPlayIdList().size() > 0) {
			if (utrainingDao.buildUserTrainingRelation(vo) != vo.getTrainingPlayIdList().size()) {
				id = "";
			}
		}
		if (vo.getTaskList() != null && vo.getTaskList().size() > 0) {
			List<String> taskIds = new ArrayList<String>();
			for (UtrainingTaskVO task : vo.getTaskList()) {
				String taskId = TaskDaoFact.getInstance(task).addEdit2DB(utrainingDao, task, bo.getOrgId());
				if (!StringUtil.isBlank(taskId)) {
					taskIds.add(taskId);
					if (task.getEvaList() != null && task.getEvaList().size() > 0) {
						for (UtrainingTaskEvaluationVO eva : task.getEvaList()) {
							String evaId = utrainingDao.handleTaskEvaluation(taskId, eva);
						}
					}
					
					if (task.getSingleTrainingList() != null && task.getSingleTrainingList().size() > 0) {
						utrainingDao.buildTaskSingleTrainingRelation(taskId, task.getSingleTrainingList());
					}
					
				}
			}
			if (taskIds.size() == vo.getTaskList().size()) {
				if (taskIds.size() != utrainingDao.buildTaskTrainingRelation(id, taskIds)) {
					id = "";
				}
			} else {
				id = "";
			}
		}
		return id;
	}

	@Override
	public List<UtrainingBO> getUtrainingInYear(String year, String orgId) {
		List<UtrainingBO> bolist = new ArrayList<UtrainingBO>();
		List<UtrainingVO> voList = utrainingDao.getUtrainingInYear(year, orgId);
		UtrainingBO bo = null;
		if (voList != null && voList.size() > 0) {
			for (UtrainingVO vo : voList) {
				bo = new UtrainingBO();
				BeanUtils.copyProperties(vo, bo);
				bolist.add(bo);
			}
		}
		return bolist;
	}

	@Override
	public List<UtrainingVO> getUtrainingsByUserId(String userId) {
		List<UtrainingVO> trainingList = utrainingDao.getUtrainingsByUserId(userId);

		if (trainingList == null) {
			trainingList = new ArrayList<UtrainingVO>();
		}

		return trainingList;
	}

	@Override
	public UtrainingBO getUtrainingById(String utrainingId) {
		UtrainingVO vo = utrainingDao.getUtrainingById(utrainingId);
		UtrainingBO bo = new UtrainingBO();

		List<String> userIdList = utrainingDao.getPlayersInTraining(utrainingId);

		List<String> taskIdList = utrainingDao.getTasksInTraining(utrainingId);
		List<UtrainingTaskVO> taskList = null;

		if (taskIdList != null && taskIdList.size() > 0) {
			taskList = utrainingDao.getTaskInfoById(taskIdList);
			List<MatchTaskVO> matchTaskMapings = utrainingDao.getMatchTaskInfoByIds(taskIdList);
			if (matchTaskMapings != null) {
				for (int i = 0; i < matchTaskMapings.size(); i++) {
					MatchTaskVO matchTaskVO = matchTaskMapings.get(i);
					setMatchID4Task(taskList, matchTaskVO);
				}
			}
			
		}
		if (taskList != null && taskList.size() > 0) {
			for (UtrainingTaskVO task : taskList) {
				List<UtrainingTaskEvaluationVO> evaList = utrainingDao.getEvaListByTask(task.getId());
				if (evaList != null && evaList.size() > 0) {
					task.setEvaList(evaList);
				}
				
				List<String> singleTrainingIDList = utrainingDao.getSingleTrainingIDListByTaskID(task.getId());
				if (singleTrainingIDList != null && singleTrainingIDList.size() > 0) {
					task.setSingleTrainingList(singleTrainingIDList);
				}
				
			}
		}

		if (vo != null) {
			BeanUtils.copyProperties(vo, bo);
			bo.setTrainingPlayIdList(userIdList);
			bo.setTaskList(taskList);
			return bo;
		} else {
			return null;
		}
	}
	
	private void setMatchID4Task(List<UtrainingTaskVO> taskList, MatchTaskVO matchTaskVO) {
		if (taskList != null && matchTaskVO != null) {
			for (int i = 0; i < taskList.size(); i++) {
				UtrainingTaskVO vo = taskList.get(i);
				if (ITaskDao.MATCH_TASK_TYPE.equals(vo.getType()) && vo.getId().equals(matchTaskVO.getTrainingTaskId())) {
					addTrainingExtVO(matchTaskVO, vo, UtrainingController.KEY_TASK_EXT_TASK_MATCH_ID, String.valueOf(matchTaskVO.getMatchID()));
					addTrainingExtVO(matchTaskVO, vo, UtrainingController.KEY_TASK_EXT_TASK_MATCH_OPPONENT, matchTaskVO.getOpponent());
					addTrainingExtVO(matchTaskVO, vo, UtrainingController.KEY_TASK_EXT_TASK_MATCH_DRESS, matchTaskVO.getDress());
					vo.setTaskDate(matchTaskVO.getDate());
					vo.setTaskTime(matchTaskVO.getTime());
				}
			}
		}
	}

	private void addTrainingExtVO(MatchTaskVO matchTaskVO, UtrainingTaskVO vo, String itemName, String itemValue) {
		TrainingExtVO extVO = new TrainingExtVO();
		extVO.setItemName(itemName);
		extVO.setItemValue(itemValue);
		vo.getExtList().remove(extVO);
		vo.getExtList().add(extVO);
	}

	@Override
	@Transactional
	public boolean deleteUtrainingById(String utrainingId, String orgID) {
		return utrainingDao.deleteUtrainingById(utrainingId, orgID);
	}

	@Override
	@Transactional
	public boolean updateUtrainingById(UtrainingBO bo) {
		UtrainingVO vo = new UtrainingVO();
		if (bo != null) {
			BeanUtils.copyProperties(bo, vo);
			vo.setTrainingPlayIdList(bo.getTrainingPlayIdList());
		}

		boolean b = utrainingDao.updateUtrainingById(vo);
		if (vo.getTrainingPlayIdList() != null && vo.getTrainingPlayIdList().size() > 0) {
			utrainingDao.buildUserTrainingRelation(vo);
		} else {
			utrainingDao.deleteUserInTraining(vo.getId());
		}

		List<String> taskIdListInDB = utrainingDao.getTasksInTraining(bo.getId());
		List<String> deleteList = new ArrayList<String>();
		List<String> taskIds = new ArrayList<String>();

		if (null != bo.getTaskList() && bo.getTaskList().size() > 0) {
			for (UtrainingTaskVO task : bo.getTaskList()) {
				if (!StringUtil.isBlank(task.getId()) && !taskIdListInDB.contains(task.getId())) {
					deleteList.add(task.getId());
				}

				String taskId = TaskDaoFact.getInstance(task).addEdit2DB(utrainingDao, task, bo.getOrgId());
				if (!StringUtil.isBlank(taskId)) {
					taskIds.add(taskId);
					List<String> evaIdsInDB = utrainingDao.getEvasInTask(taskId);
					if (task.getEvaList() != null && task.getEvaList().size() > 0) {
						
						for (UtrainingTaskEvaluationVO eva : task.getEvaList()) {
							int index = evaIdsInDB.indexOf(eva.getId());
							if (vo.getTrainingPlayIdList() != null) {
								if (!vo.getTrainingPlayIdList().contains(eva.getUserId())) {
									continue;
								}
							}
							if (!StringUtil.isBlank(eva.getId()) && index >= 0) {
								evaIdsInDB.remove(index);
							}
							utrainingDao.handleTaskEvaluation(taskId, eva);
						}
						if (evaIdsInDB.size() > 0) {
							utrainingDao.deleteEvaListByTaskIdAndEvaIds(evaIdsInDB,taskId);
						}
					} else {
						if (evaIdsInDB.size() > 0) {
							utrainingDao.deleteEvaListByTaskIdAndEvaIds(evaIdsInDB,taskId);
						}
					}
					
					if (task.getSingleTrainingList() != null && task.getSingleTrainingList().size() > 0) {
						utrainingDao.buildTaskSingleTrainingRelation(taskId, task.getSingleTrainingList());
					}
				}

			}
			if (deleteList.size() > 0) {
				utrainingDao.deleteTaskByTrainingIdAndTaskId(deleteList, bo.getId());
			}

			if (taskIds.size() == vo.getTaskList().size()) {
				utrainingDao.buildTaskTrainingRelation(bo.getId(), taskIds);
			} else {
				b = false;
			}

		} else {
			if(taskIdListInDB!=null&&taskIdListInDB.size()>0){
				utrainingDao.deleteTaskByTrainingIdAndTaskId(taskIdListInDB, bo.getId());
			}
		}

		return b;
	}

	@Override
	public boolean checkUtrainingDate(String utrainingId, Date startDate, Date endDate, String orgId) {
		return utrainingDao.checkUtrainingDate(utrainingId, startDate, endDate, orgId);
	}
	
	@Override
	public ShareLink getShareLink(String orgId, String utrainingId) {
		ShareLink shareLink = new ShareLink("/utraining/share_plan");
		shareLink.addParameter("orgId", orgId);
		shareLink.addParameter("utrainingId", String.valueOf(utrainingId));
		return shareLink;
	}

	@Override
	public UtrainingTaskVO getLatestTrainingTask(String orgId) {
		return utrainingDao.getLatestTrainingTask(orgId);
	}

	@Override
	public List<UtrainingTaskEvaBO> getTrainingTaskEvaResults(String taskId, String orgId) {
		List<UtrainingTaskEvaluationVO> taskEvaList = utrainingDao.getTrainingTaskEvaResults(taskId);
		List<UserVO> playerList = userDao.getPlayersByOrgId(orgId);
		
		List<UtrainingTaskEvaBO> trainingTaskEvaList = new ArrayList<UtrainingTaskEvaBO>();
		
		int topThree = 3;
		
		String curScore = "";
		for (UtrainingTaskEvaluationVO evaObj: taskEvaList) {
			UtrainingTaskEvaBO curTaskEva;
			if (evaObj.getScore().isEmpty()) {
				break;
			}
			if (!curScore.equals(evaObj.getScore())) {
				if (trainingTaskEvaList.size() >= topThree) {
					break;
				} 
				curScore = evaObj.getScore();
				curTaskEva = new UtrainingTaskEvaBO();
				curTaskEva.setScore(Double.parseDouble(curScore));
				curTaskEva.setPlayerList(new ArrayList<UserVO>());
				curTaskEva.setPlayerInfoList(new ArrayList<String>());
				curTaskEva.setTopFourPlayerInfoList(new ArrayList<String>());
				trainingTaskEvaList.add(curTaskEva);
			} else {
				curTaskEva = trainingTaskEvaList.get(trainingTaskEvaList.size() - 1);
			}
			
			for (UserVO player: playerList) {
				if (player.getId().equals(evaObj.getUserId())) {
					curTaskEva.getPlayerList().add(player);
					
					String playerNumber = player.getUserExtInfoMap().get("professional_jersey_number");
					if (StringUtils.isBlank(playerNumber)) {
						playerNumber = "--";
					}
					String playerName = player.getName();
					String playerInfo = playerNumber + "号 " + playerName;
					if (curTaskEva.getAllPlayersInfo() == null) {
						curTaskEva.setAllPlayersInfo(playerInfo);
					} else {
						curTaskEva.setAllPlayersInfo(curTaskEva.getAllPlayersInfo() + " " + playerInfo);
					}
					curTaskEva.getPlayerInfoList().add(playerInfo);
					if (curTaskEva.getTopFourPlayerInfoList().size() < 4) {
						curTaskEva.getTopFourPlayerInfoList().add(playerInfo);
					}
					break;
				}
			}
		}
		
		return trainingTaskEvaList;
	}
	
	@Override
	public List<UtrainingTaskVO> getComingTrainingTask(String orgId) {
		return utrainingDao.getComingTrainingTask(orgId);
	}
	
	@Override
	public List<TrainingMatchVO> getUtrainingMatchInOneYear(String year, String orgID) {
		return utrainingDao.getUtrainingMatchInOneYear(year, orgID);
	}
}
