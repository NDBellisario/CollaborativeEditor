package model;

public class Revision {
		
		//private int revisionCount;
		private User revisor;
		private String revision;
		
		public Revision(User user, String text) {//, int count) {
			//revisionCount = count;
			revisor = user;
			revision = text;
		}

	}