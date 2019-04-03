package org.lpdql.dragon.interfaces;

import org.lpdql.dragon.scenario.Story;

public interface StoryElement {

    Story storyElement = null;

    public void storyDone();

    public void setStoryElement(Story element);

    public boolean containStoryElement();

    public Story getStoryElement();

}
