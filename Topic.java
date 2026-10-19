public class Topic implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private final String code;
    private final String name;
    private final String[] subtopics;
    private final boolean[] higherLevelOnly;

    public Topic(String code, String name, String[] standardLevelSubtopics,
            String... higherLevelSubtopics) {
        this.code = code;
        this.name = name;
        this.subtopics = new String[standardLevelSubtopics.length + higherLevelSubtopics.length];
        this.higherLevelOnly = new boolean[this.subtopics.length];
        System.arraycopy(standardLevelSubtopics, 0, this.subtopics, 0, standardLevelSubtopics.length);
        System.arraycopy(higherLevelSubtopics, 0, this.subtopics, standardLevelSubtopics.length,
            higherLevelSubtopics.length);
        for (int i = standardLevelSubtopics.length; i < this.subtopics.length; i++) {
            this.higherLevelOnly[i] = true;
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String[] getSubtopics() {
        return subtopics.clone();
    }

    public boolean isHigherLevelOnly(int subtopicIndex) {
        return higherLevelOnly[subtopicIndex];
    }
}
