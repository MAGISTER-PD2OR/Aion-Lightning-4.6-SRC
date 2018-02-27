package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.skill.PlayerSkillEntry;
import com.aionemu.gameserver.model.skill.PlayerSkillList;
import com.aionemu.gameserver.services.SkillLearnService;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import java.util.List;

/**
 * Created by Kill3r
 */
public class CmdDeleteSkill extends AbstractGMHandler {

    public CmdDeleteSkill(Player admin, String params){
        super(admin,params);
        run();
    }

    public void run(){
        String skillName = params;
        PlayerSkillList playerSkillList = admin.getSkillList();
        List<SkillTemplate> skill = DataManager.SKILL_DATA.getSkillTemplates();

        if(skillName.contains("_G")){
            skillName = "SKILL_" + params;
            if(checkLevel(params) == 1){
                skillName = skillName.replaceAll("_G1", "");
            }else if(checkLevel(params) == 2){
                skillName = skillName.replaceAll("_G2", "");
            }else if(checkLevel(params) == 3){
                skillName = skillName.replaceAll("_G3", "");
            }else if(checkLevel(params) == 4){
                skillName = skillName.replaceAll("_G4", "");
            }else if(checkLevel(params) == 5){
                skillName = skillName.replaceAll("_G5", "");
            }else if(checkLevel(params) == 6){
                skillName = skillName.replaceAll("_G6", "");
            }else if(checkLevel(params) == 7){
                skillName = skillName.replaceAll("_G7", "");
            }else if(checkLevel(params) == 8){
                skillName = skillName.replaceAll("_G8", "");
            }else if(checkLevel(params) == 9){
                skillName = skillName.replaceAll("_G9", "");
            }else if(checkLevel(params) == 10){
                skillName = skillName.replaceAll("_G10", "");
            }
        }else{
            skillName = params;
        }

        for(SkillTemplate s : skill){
            if(s.getStack().equalsIgnoreCase(skillName)){
                for (PlayerSkillEntry skillEntry : playerSkillList.getAllSkills()) {
                    if (!skillEntry.isStigma()) {
                        SkillLearnService.removeSkill(admin, skillEntry.getSkillId());
                    }
                }
            }
        }

    }

    private int checkLevel(String string){
        if(string.endsWith("G1")){
            return 1;
        }else if(string.endsWith("G2")){
            return 2;
        }else if(string.endsWith("G3")){
            return 3;
        }else if(string.endsWith("G4")){
            return 4;
        }else if(string.endsWith("G5")){
            return 5;
        }else if(string.endsWith("G6")){
            return 6;
        }else if(string.endsWith("G7")){
            return 7;
        }else{
            return 1;
        }
    }
}
