public enum  Party {
    REP(), DEM(), LIB(), GRN(), CNS(), TEA(), ARR(), UNK();

    public static Party fromString(String s) {
        switch (s.toUpperCase()) {
            case "REP":
                return REP;
            case "DEM":
                return DEM;
            case "LIB":
                return LIB;
            case "GRN":
                return GRN;
            case "CNS":
                return CNS;
            case "TEA":
                return TEA;
            case "ARR":
                return ARR;
            default:
                return UNK;
        }
    }

    @Override
    public String toString(){
        switch(this) {
            case REP:
                return "REP";
            case DEM:
                return "DEM";
            case LIB:
                return "LIB";
            case GRN:
                return "GRN";
            case CNS:
                return "CNS";
            case TEA:
                return "TEA";
            case ARR:
                return "ARR";
            default:
                return "UNK";
        }
    }
}