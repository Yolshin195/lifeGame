package com.company.lifegame.screen.skill;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Skill;

@UiController("lg_Skill.browse")
@UiDescriptor("skill-browse.xml")
@LookupComponent("skillsTable")
public class SkillBrowse extends StandardLookup<Skill> {
}