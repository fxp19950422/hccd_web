package cn.sportsdata.webapp.youth.common.vo.role;
import cn.sportsdata.webapp.youth.common.vo.BaseVO;
/**
 * Created by binzhu on 4/28/16.
 */
public class RoleVO extends BaseVO{
	public final static long STAFF_COACH = 1;
	
    private static final long serialVersionUID = 7922477308103836118L;

    private long id;
    private String name;
    private String role_type;

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = name;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
