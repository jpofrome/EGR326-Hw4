import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Election {
    //variables
    private List<Candidate> candidates;
    private List<Poll> pollList;
    private boolean isOpen = false;

    //constructor
    public Election() {
        candidates = new ArrayList<>();
        pollList = new ArrayList<>();
        producer();
    }

    //puts candidates from txt to list
    private void producer() {
        try {
            List<String> candidatesTxt = Files.readAllLines(Paths.get("candidates.txt"));
            for (String part : candidatesTxt) {
                String[] attributes = part.split(",");
                candidates.add(new Candidate(attributes[0], attributes[1]));
            }
        } catch (Exception e) {
            System.out.println("error.");
        }
    }

    //add Polling location
    public boolean addPollingLocation(String newPoll) {
        Poll poll = new Poll(newPoll);
        return pollList.add(poll);
    }

    //count the votes
    public void countVotes(String fileName){
        for(Poll each : pollList) {
            if(each.getFileName().equals(fileName))
                each.distributeVotes(candidates);
        }
    }

    //eliminate worst candidate
    public Candidate eliminate() {
        Candidate worst = candidates.get(0);
        int worstVotes = candidates.get(0).getVotes();
        int index = 0;

        for(int i = 0; i < candidates.size(); i++) {
            if(!candidates.get(i).isEliminated() && candidates.get(i).getVotes() < worstVotes) {
                worst = candidates.get(i);
                worstVotes = candidates.get(i).getVotes();
                index = i;
            }
        }

        candidates.get(index).setEliminated(true);

        for(Candidate c : candidates) {
            c.resetVotes();
        }
        for(Poll p : pollList) {
            countVotes(p.getFileName());
        }

        return worst;
    }

    //distributes votes
    public void distributeVotes(String fileName){
        for(Poll p : pollList) {
            if(p.getFileName().equals(fileName))
                p.distributeVotes(candidates);
        }
    }

    //Check results of each polling place
    public Election indvLocationResults(String location) {
        for(Poll p : pollList) {
            if(p.equals(new Poll(location))){
                Election local = new Election();
                for(Candidate c : candidates){
                    if(c.isEliminated()){
                        for(Candidate eachLocalCandidate : local.getCandidates()){
                            if(c.equals(eachLocalCandidate)){
                                eachLocalCandidate.setEliminated(true);
                            }
                        }
                    }
                }
                local.addPollingLocation(location);
                local.distributeVotes(location);
                return local;
            }
        }
        return null;
    }

    //find total votes
    public int totalVotes(){
        int total = 0;
        for(Candidate c : candidates){
            if(!c.isEliminated())
                total += c.getVotes();
        }
        return total;
    }

    //find if there is a majority winner
    public boolean isMajorityWinner(){
        for(Candidate c : candidates){
            if(c.getVotes()*2 > totalVotes()){
                return true;
            }
        }
        return false;
    }

    //candidate is eliminated
    public boolean eliminated(Candidate canidate) {return false;}

    //get results
    public List<Candidate> results() {
        return candidates;
    }

    //getters
    public boolean isOpen() {return isOpen;}
    public List<Candidate> getCandidates() {return candidates;}
    public List<Poll> getPolls() {return pollList;}

    //setter
    public void closeElection() {isOpen = false;}
    public void openElection() {isOpen = true;}
    public void setCandidates(List<Candidate> c) {candidates = c;}
    public void setPolls(List<Poll> p) {pollList = p;}


}