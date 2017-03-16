SET FOREIGN_KEY_CHECKS=0;
use soccerpro;
SET names utf8;

DROP PROCEDURE IF EXISTS CheckTableExist;
DROP PROCEDURE IF EXISTS CheckColumnExist;
DROP PROCEDURE IF EXISTS CheckIndexExist;
DROP PROCEDURE IF EXISTS CheckConstraintExist;
DROP PROCEDURE IF EXISTS CheckPrimaryKeyExist;
DROP PROCEDURE IF EXISTS CheckDataExist;
DROP PROCEDURE IF EXISTS AddRoleTableRecords;
DROP PROCEDURE IF EXISTS AddPrivilegeTableRecords;
DROP PROCEDURE IF EXISTS AddRolePrivilegeTableRecords;
DROP PROCEDURE IF EXISTS AddStatusColumn4User;
DROP PROCEDURE IF EXISTS AddTacticsBasicData;
DROP PROCEDURE IF EXISTS ALTERTaskExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERTaskEvaluationExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERUserExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERSingleTrainingExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERTrainingTaskEvaluationForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERTrainingTaskEvaluationExtItemNameType;
DROP PROCEDURE IF EXISTS ALTERTrainingTaskEvaluationScoreType;
DROP PROCEDURE IF EXISTS CreateTableFormation;

DELIMITER //
CREATE PROCEDURE CheckTableExist(IN p_tablename varchar(64), OUT ret int)
BEGIN
	SELECT p_tablename;
    SET ret = 0;
    SELECT COUNT(*) INTO @cnt FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = p_tablename;
    IF @cnt >0 THEN
        SET ret = 1;
    END IF;
END//


DELIMITER //
CREATE PROCEDURE CheckColumnExist(IN p_tablename varchar(64), IN p_columnname varchar(64), OUT ret int)
BEGIN
	SELECT p_tablename;
    SET ret = 0;
    SELECT COUNT(*) INTO @cnt FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = p_tablename AND COLUMN_NAME = p_columnname AND TABLE_SCHEMA=database();
    IF @cnt >0 THEN
        SET ret = 1;
    END IF;
END//

DELIMITER //
CREATE PROCEDURE CheckIndexExist(IN p_tablename varchar(64), IN p_indexname varchar(64), OUT ret int)
BEGIN
	SELECT p_tablename;
    SET ret = 0;
    SELECT COUNT(*) INTO @cnt FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = p_tablename AND INDEX_NAME = p_indexname AND TABLE_SCHEMA=database();
    IF @cnt >0 THEN
        SET ret = 1;
    END IF;
END//

DELIMITER //
CREATE PROCEDURE CheckConstraintExist(IN p_tablename varchar(64), IN p_constraintname varchar(64), OUT ret int)
BEGIN
	SELECT p_tablename;
    SET ret = 0;
    SELECT COUNT(*) INTO @cnt FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_NAME = p_tablename AND CONSTRAINT_NAME = p_constraintname AND TABLE_SCHEMA=database();
    IF @cnt >0 then
        SET ret = 1;
    END IF;
END//

DELIMITER //
CREATE PROCEDURE CheckDataExist(IN p_tablename varchar(64), IN str_condition varchar(64), OUT ret int)
BEGIN
	SELECT p_tablename;
	SET ret = 0;
	SET @STMT :=CONCAT("SELECT COUNT(*) INTO @cnt from  ", p_tablename, " where ", str_condition);
	PREPARE STMT FROM @STMT;
	EXECUTE STMT;
	IF @cnt >0 THEN
		SET ret = 1;
	END IF;
END//

DELIMITER //
CREATE PROCEDURE CheckPrimaryKeyExist(IN p_tablename varchar(64), OUT ret int)
BEGIN
	SELECT p_tablename;
	SET ret = 0;
	SELECT COUNT(*) INTO @cnt from INFORMATION_SCHEMA.KEY_COLUMN_USAGE where TABLE_NAME = p_tablename AND TABLE_SCHEMA=database();
	IF @cnt >0 then
    	SET ret = 1;
END IF;
END//


DELIMITER //
CREATE PROCEDURE AddRoleTableRecords()
BEGIN
    SET @ret = 0;
    CALL CheckDataExist("role", "id=1", @ret);
    IF @ret = 0 THEN
        INSERT INTO role(id, name, created_time) values 
        (1, 'staff_coach', now()),
        (2, 'assist_coach', now()),
        (3, 'fitness_coach', now()),
        (4, 'player', now()),
        (5, 'administrator', now()),
        (6, 'coach', now());
    END IF;
END//

DELIMITER //
CREATE PROCEDURE AddPrivilegeTableRecords()
BEGIN
    SET @ret = 0;
    CALL CheckDataExist("privilege", "id=1", @ret);
    IF @ret = 0 THEN
        INSERT INTO privilege(id, parent_id, item_name, item_value, description, created_time) values 
        (1, NULL, 'people_manage', NULL, 'people manage 1st menu', now()),
        (2, NULL, 'regular_manage', NULL, 'regular manage 1st menu', now()),
        (3, NULL, 'match_manage', NULL, 'people manage 1st menu', now()),
        (4, NULL, 'auxiliary_manage', NULL, 'auxiliary manage 1st menu', now()),
        (5, 1, 'user/showCoachList', 'user/showCoachList', 'coach manage 2st menu', now()),
        (6, 1, 'user/showPlayerList', 'user/showPlayerList', 'player manage 2st menu', now()),
        (7, 2, 'utraining_manager', 'utraining/show_calendar', 'utraining manage 2st menu', now());
    END IF;
    CALL CheckDataExist("privilege", "id=8", @ret);
    IF @ret = 0 THEN
        INSERT INTO privilege(id, parent_id, item_name, item_value, description, created_time) values 
        (8, 4, 'system_tool/showTacticsList', 'system_tool/showTacticsList', 'system tool 2st menu', now());
     END IF;
     
    CALL CheckDataExist("privilege", "id=9", @ret);
    IF @ret = 0 THEN
        INSERT INTO privilege(id, parent_id, item_name, item_value, description, created_time) values 
        (9, NULL, 'tactics_manage', NULL, 'tactics manage 1st menu', now()),
        (10, 9, 'starters_setting', 'tactics/showStarterSettings', 'starters setting 2st menu', now()),
        (11, 9, 'place_kick', 'placeKick/showPlaceKickList', 'set pieces 2st menu', now());
     END IF;
END//

DELIMITER //
CREATE PROCEDURE AddRolePrivilegeTableRecords()
BEGIN
    SET @ret = 0;
    CALL CheckDataExist("role_privilege_association", "role_id=1", @ret);
    IF @ret = 0 THEN
        INSERT INTO role_privilege_association(role_id, privilege_id, created_time) values 
        (1, 1, now()),
        (1, 2, now()),
        (1, 3, now()),
        (1, 4, now()),
        (1, 5, now()),
        (1, 6, now()),
        (1, 7, now()),
        (1, 9, now()),
        (2, 1, now()),
        (2, 2, now()),
        (2, 3, now()),
        (2, 4, now()),
        (2, 5, now()),
        (2, 6, now()),
        (2, 7, now()),
        (2, 9, now()),
        (3, 1, now()),
        (3, 2, now()),
        (3, 3, now()),
        (3, 4, now()),
        (3, 5, now()),
        (3, 6, now()),
        (3, 7, now()),
        (3, 9, now());
    END IF;
END//

DELIMITER //
CREATE PROCEDURE AddStatusColumn4User()
BEGIN
    SET @ret = 0;
    CALL CheckColumnExist("user", "status", @ret);
    IF @ret = 0 THEN
    	ALTER TABLE `user` ADD `status` ENUM('active','inactive','deleted') NULL DEFAULT 'active' AFTER `name`;
		UPDATE `user` SET `status` = 'active';
    END IF;
END//

DELIMITER //
CREATE PROCEDURE AddTacticsBasicData()
BEGIN
    SET @ret = 0;
    CALL CheckTableExist("tactics_playground", @ret);
    IF @ret = 1 THEN
		replace INTO `soccerpro`.`tactics_playground` (`id`, `name`, `abbr`, `backgroundimg`, `category`) VALUES ('1', '全场(横)', 'stype1','strategy_background1.png', 'soccer');
		replace INTO `soccerpro`.`tactics_playground` (`id`, `name`, `abbr`, `backgroundimg`, `category`) VALUES ('2', '半场(进攻)', 'stype2','strategy_background2.png', 'soccer');
		replace INTO `soccerpro`.`tactics_playground` (`id`, `name`, `abbr`, `backgroundimg`, `category`) VALUES ('3', '半场(防守)', 'stype3','strategy_background3.png', 'soccer');
		replace INTO `soccerpro`.`tactics_playground` (`id`, `name`, `abbr`, `backgroundimg`, `category`) VALUES ('4', '无场地', 'stype4','strategy_background4.png', 'soccer');
    END IF;
    CALL CheckTableExist("tactics_type", @ret);
    IF @ret = 1 THEN
    	replace INTO `soccerpro`.`tactics_type` (`id`, `name`, `category`) VALUES ('1', '全部分类', 'soccer');
    	replace INTO `soccerpro`.`tactics_type` (`id`, `name`, `category`) VALUES ('2', '未设置', 'soccer');
    	replace INTO `soccerpro`.`tactics_type` (`id`, `name`, `category`) VALUES ('3', '进攻-阵地战', 'soccer');
		replace INTO `soccerpro`.`tactics_type` (`id`, `name`, `category`) VALUES ('4', '进攻-任意球', 'soccer');
		replace INTO `soccerpro`.`tactics_type` (`id`, `name`, `category`) VALUES ('5', '防守-任意球', 'soccer');
		replace INTO `soccerpro`.`tactics_type` (`id`, `name`, `category`) VALUES ('6', '首发设置', 'soccer');
    END IF;
    
    CALL CheckTableExist("place_kick_type", @ret);
    IF @ret = 1 THEN
    	replace INTO `soccerpro`.`place_kick_type` (`id`, `name`, `category`) VALUES ('1', '全部分类', 'soccer');
    	replace INTO `soccerpro`.`place_kick_type` (`id`, `name`, `category`) VALUES ('2', '未设置', 'soccer');
    	replace INTO `soccerpro`.`place_kick_type` (`id`, `name`, `category`) VALUES ('3', '进攻-阵地战', 'soccer');
		replace INTO `soccerpro`.`place_kick_type` (`id`, `name`, `category`) VALUES ('4', '进攻-任意球', 'soccer');
		replace INTO `soccerpro`.`place_kick_type` (`id`, `name`, `category`) VALUES ('5', '防守-任意球', 'soccer');
    END IF;
END//

CREATE PROCEDURE ALTERTaskExtForeignKeyType()
BEGIN
	SET @ret = 0;
    CALL CheckTableExist("training_task_ext", @ret);
     IF @ret = 1 THEN
		ALTER TABLE `training_task_ext` 
		DROP FOREIGN KEY `training_task_ext_fk`;
		ALTER TABLE `training_task_ext` 
		ADD CONSTRAINT `training_task_ext_fk`
		  FOREIGN KEY (`task_id`)
		  REFERENCES `training_task` (`id`)
		  ON DELETE CASCADE
		  ON UPDATE NO ACTION;	
	END IF;
END//

DELIMITER //

CREATE PROCEDURE ALTERTaskEvaluationExtForeignKeyType()
BEGIN
	SET @ret = 0;
    CALL CheckTableExist("training_task_evaluation_ext", @ret);
     IF @ret = 1 THEN
		ALTER TABLE `training_task_evaluation_ext` 
		DROP FOREIGN KEY `training_task_evaluation_ext_fk`;
		ALTER TABLE `training_task_evaluation_ext` 
		ADD CONSTRAINT `training_task_evaluation_ext_fk`
		  FOREIGN KEY (`task_evaluation_id`)
		  REFERENCES `training_task_evaluation` (`id`)
		  ON DELETE CASCADE
		  ON UPDATE NO ACTION;	
	END IF;
END//

DELIMITER //

CREATE PROCEDURE ALTERUserExtForeignKeyType()
BEGIN
	SET @ret = 0;
    CALL CheckTableExist("user_ext", @ret);
     IF @ret = 1 THEN
		ALTER TABLE `user_ext` 
		DROP FOREIGN KEY `user_ext_fk`;
		ALTER TABLE `user_ext` 
		ADD CONSTRAINT `user_ext_fk`
		  FOREIGN KEY (`user_id`)
		  REFERENCES `user` (`id`)
		  ON DELETE CASCADE
		  ON UPDATE NO ACTION;
	END IF;
END//

DELIMITER //

CREATE PROCEDURE ALTERSingleTrainingExtForeignKeyType()
BEGIN
	SET @ret = 0;
    CALL CheckTableExist("single_training_ext", @ret);
     IF @ret = 1 THEN
		ALTER TABLE `single_training_ext` 
		DROP FOREIGN KEY `single_training_ext_fk`;
		ALTER TABLE `single_training_ext` 
		ADD CONSTRAINT `single_training_ext_fk`
		  FOREIGN KEY (`single_training_id`)
		  REFERENCES `single_training` (`id`)
		  ON DELETE CASCADE
		  ON UPDATE NO ACTION;
	END IF;
END//

DELIMITER //

CREATE PROCEDURE ALTERTrainingTaskEvaluationForeignKeyType()
BEGIN
	SET @ret = 0;
    CALL CheckTableExist("training_task_evaluation", @ret);
     IF @ret = 1 THEN
		ALTER TABLE `training_task_evaluation` 
		DROP FOREIGN KEY `training_task_evaluation_fk1`;
		ALTER TABLE `training_task_evaluation` 
		ADD CONSTRAINT `training_task_evaluation_fk1`
		  FOREIGN KEY (`task_id`)
		  REFERENCES `training_task` (`id`)
		  ON DELETE CASCADE
		  ON UPDATE NO ACTION;

	END IF;
END//


DELIMITER //

CREATE PROCEDURE ALTERTrainingTaskEvaluationExtItemNameType()
BEGIN
	SET @ret = 0;
    CALL CheckTableExist("training_task_evaluation_ext", @ret);
     IF @ret = 1 THEN
		ALTER TABLE `training_task_evaluation_ext` 
		CHANGE COLUMN `item_name` `item_name` VARCHAR(128) NOT NULL COMMENT '' ;
	END IF;
END//


DELIMITER //

CREATE PROCEDURE ALTERTrainingTaskEvaluationScoreType()
BEGIN
	SET @ret = 0;
    CALL CheckTableExist("training_task_evaluation", @ret);
     IF @ret = 1 THEN
		ALTER TABLE `training_task_evaluation` 
		CHANGE COLUMN `score` `score` VARCHAR(100) NULL DEFAULT '0' COMMENT '' ;
	END IF;
END//

CREATE PROCEDURE CreateTableFormation()
BEGIN	  
	SET @ret = 0;
    CALL CheckTableExist("formation_type", @ret);
     IF @ret = 1 THEN
		replace INTO `soccerpro`.`formation_type` (`id`, `name`, `category`, `status`) VALUES ('1', '未设置', 'soccer', 'active');
		replace INTO `soccerpro`.`formation_type` (`id`, `name`, `category`, `status`) VALUES ('2', '4-4-2', 'soccer', 'active');
		replace INTO `soccerpro`.`formation_type` (`id`, `name`, `category`, `status`) VALUES ('3', '3-5-2', 'soccer', 'active');
		replace INTO `soccerpro`.`formation_type` (`id`, `name`, `category`, `status`) VALUES ('4', '4-5-1', 'soccer', 'active');
		replace INTO `soccerpro`.`formation_type` (`id`, `name`, `category`, `status`) VALUES ('5', '4-3-3', 'soccer', 'active');
		replace INTO `soccerpro`.`formation_type` (`id`, `name`, `category`, `status`) VALUES ('6', '3-4-3', 'soccer', 'active');
	END IF;
END//

DELIMITER ;

CALL AddRoleTableRecords();
CALL AddPrivilegeTableRecords();
CALL AddRolePrivilegeTableRecords();
CALL AddStatusColumn4User();
CALL AddTacticsBasicData();
CALL ALTERTaskExtForeignKeyType();
CALL ALTERTaskEvaluationExtForeignKeyType();
CALL ALTERUserExtForeignKeyType();
CALL ALTERSingleTrainingExtForeignKeyType();
CALL ALTERTrainingTaskEvaluationForeignKeyType();
CALL ALTERTrainingTaskEvaluationExtItemNameType();
CALL ALTERTrainingTaskEvaluationScoreType();
CALL CreateTableFormation();

DROP PROCEDURE IF EXISTS CheckTableExist;
DROP PROCEDURE IF EXISTS CheckColumnExist;
DROP PROCEDURE IF EXISTS CheckIndexExist;
DROP PROCEDURE IF EXISTS CheckConstraintExist;
DROP PROCEDURE IF EXISTS CheckPrimaryKeyExist;
DROP PROCEDURE IF EXISTS CheckDataExist;
DROP PROCEDURE IF EXISTS AddRoleTableRecords;
DROP PROCEDURE IF EXISTS AddPrivilegeTableRecords;
DROP PROCEDURE IF EXISTS AddRolePrivilegeTableRecords;
DROP PROCEDURE IF EXISTS AddStatusColumn4User;
DROP PROCEDURE IF EXISTS AddTacticsBasicData;
DROP PROCEDURE IF EXISTS ALTERTaskExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERTaskEvaluationExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERUserExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERSingleTrainingExtForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERTrainingTaskEvaluationForeignKeyType;
DROP PROCEDURE IF EXISTS ALTERTrainingTaskEvaluationExtItemNameType;
DROP PROCEDURE IF EXISTS ALTERTrainingTaskEvaluationScoreType;
DROP PROCEDURE IF EXISTS CreateTableFormation;