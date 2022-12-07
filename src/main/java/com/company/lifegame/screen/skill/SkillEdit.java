package com.company.lifegame.screen.skill;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Skill;

@UiController("lg_Skill.edit")
@UiDescriptor("skill-edit.xml")
@EditedEntityContainer("skillDc")
public class SkillEdit extends StandardEditor<Skill> {
}