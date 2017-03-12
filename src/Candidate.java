public class Candidate implements Comparable{
    //variables
    private String name;
    private Party party;
    private int votes = 0;
    private boolean eliminated;

    //constructor
    public Candidate(String name, String party) {
        this.name = name;
        this.party = Party.fromString(party);
    }

    //setters
    public void setName(String name) {this.name = name;}
    public void setParty(Party party) {this.party = party;}
    public void setVotes(int Votes) {this.votes = votes;}
    public void setEliminated(boolean eliminated) {this.eliminated = eliminated;}

    //getters
    public String getName() {return name;}
    public Party getParty() {return party;}
    public int getVotes() {return votes;}
    public boolean isEliminated() {return eliminated;}

    //add/reset vote to candidate
    public void addVote() {this.votes += 1;}
    public void resetVotes() {this.votes = 0;}

    @Override
    public boolean equals(Object obj) {
        if(obj != null && this.getClass() == obj.getClass()){
            Candidate other = (Candidate) obj;
            return this.name.equals(other.name) && this.party == other.party;
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        if(o != null && this.getClass() == o.getClass()){
            Candidate other = (Candidate) o;
            if(other.votes != this.votes)
                return other.votes - this.votes;
            else
                return other.getName().compareTo(this.getName());
        }
        return 0;
    }

    @Override
    protected Candidate clone() throws CloneNotSupportedException {
        return new Candidate(this.name, this.party.toString());
    }
}