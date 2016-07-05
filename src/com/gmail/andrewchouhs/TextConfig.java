package com.gmail.andrewchouhs;

public class TextConfig 
{
	private boolean used;
	private String topicText;
	private String correctText;
	private String incorrectText;
	
	public TextConfig(boolean used , String topicText , String correctText , String incorrectText)
	{
		this.used = used;
		this.topicText = topicText;
		this.correctText = correctText;
		this.incorrectText = incorrectText;
	}

	public boolean isUsed() 
	{
		return used;
	}

	public void setUsed(boolean used) 
	{
		this.used = used;
	}

	public String getText(AnswerStatus as)
	{
		switch(as)
		{
			case Generate:
				return topicText;
			case Correct:
				return correctText;
			case Incorrect:
				return incorrectText;
			default:
				return null;
		}
	}
}
