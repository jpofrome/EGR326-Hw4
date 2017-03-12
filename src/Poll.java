import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Poll implements Comparable{
    //variables
    private String fileName;
    private List<String> votes;

    //constructor
    public Poll(String fileName) {
        this.fileName = fileName;
        votes = new ArrayList<>();
        producer();
    }

    //gets info from txt file
    private void producer() {
        try {
            List<String> ballotTxt = Files.readAllLines(Paths.get(fileName));
            for (String line : ballotTxt) {
                this.votes.add(line);
            }
        }
        catch(Exception e){
            System.out.println("error.");
        }
    }

    //distribute the votes to election
    public void distributeVotes(List<Candidate> candidates) {
        boolean votesCast = false;

        for (String line : this.votes) {
            for(String cand : line.split(",")){
                Candidate newCand = null;
                for(Candidate candidate : candidates){
                    if(!candidate.isEliminated() && candidate.getName().equals(cand)){
                        candidate.addVote();
                        votesCast = true;
                        break;
                    } else if(!candidate.isEliminated() && isSpelledCorrect(candidate.getName(), cand)){
                        candidate.addVote();
                        votesCast = true;
                        break;
                    } else if(candidate.isEliminated() && candidate.getName().equals(cand)){
                        break;
                    } else {
                        boolean exists = false;
                        for(Candidate eachCand : candidates){
                            if(eachCand.getName().equals(cand)) {
                                exists = true;
                                break;
                            }else if(isSpelledCorrect(eachCand.getName(),cand)) {
                                exists = true;
                                break;
                            }
                        }
                        if(!exists) {
                            newCand = new Candidate(cand, "UNK");
                            newCand.addVote();
                            votesCast = true;
                            break;
                        }
                    }
                }
                if(newCand != null){
                    candidates.add(newCand);
                }
                if(votesCast)
                    break;
            }
        }
    }

    //
    public boolean isCanidateDefined(List<Candidate> canidates) {return false;}

    //checks if name is spelled correctly
    public boolean isSpelledCorrect(String a, String b) {return true;}

    //getters
    public String getFileName() {return fileName;}
    public List<String> getVotes() {return votes;}

    //setters
    public void setVotes(List<String> v) {votes = v;}

    @Override
    public int compareTo(Object o) {
        if(o != null && this.getClass() == o.getClass()){
            Poll other = (Poll) o;
            return other.votes.size() - this.votes.size();
        }
        return 0;
    }

    @Override
    protected Poll clone() throws CloneNotSupportedException {
        return new Poll(this.fileName);
    }

    @Override
    public boolean equals(Object o) {
        if(this.getClass() == o.getClass() && o != null){
            Poll other = (Poll) o;
            return this.fileName.equals(other.fileName);
        }
        return false;
    }

}