package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Kill3r
 */
public class DelayedSkill2Effect extends EffectTemplate {

    @XmlAttribute(name = "skill_id")
    protected int skilliD;
    @XmlAttribute(name = "time_delay_to_hit")
    protected int delaytohit;


    @Override
    public void applyEffect(Effect effect){
        effect.addToEffectedController();
    }

    @Override
    public void startEffect(final Effect effect){
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                // apply effect
                if(effect.getEffected().getEffectController().hasAbnormalEffect(3160)){
                    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skilliD);
                    Effect e = new Effect(effect.getEffector(), effect.getEffected(), template, template.getLvl(), 0);
                    e.initialize();
                    e.applyEffect();
                }
            }
        }, delaytohit);
    }

    @Override
    public void endEffect(Effect effect){

    }
}
