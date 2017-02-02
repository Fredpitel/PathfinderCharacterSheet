package MainApp.model;

public class Alignment {
    public enum possibleAlignments {NO_ALIGNMENT, LG,NG, CG, LN, TN, CN, LE, NE, CE};
    public static possibleAlignments[][] alignmentGrid = {{possibleAlignments.LG, possibleAlignments.NG, possibleAlignments.CG},
                                                          {possibleAlignments.LN, possibleAlignments.TN, possibleAlignments.CN},
                                                          {possibleAlignments.LE, possibleAlignments.NE, possibleAlignments.CE}};
}
