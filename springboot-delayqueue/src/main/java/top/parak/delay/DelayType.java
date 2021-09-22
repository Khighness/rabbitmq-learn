package top.parak.delay;

/**
 * @author KHighness
 * @since 2021-09-20
 */
public enum DelayType {

    DELAY_3_SECONDS(1, 3000),
    DELAY_10_SECONDS(2, 10000);

    private final int type;
    private final int delayTime;

    DelayType(int type, int delayTime) {
        this.type = type;
        this.delayTime = delayTime;
    }

    public int getType() {
        return type;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public static DelayType getDelayType(int type) {
        for (DelayType delayType : DelayType.values()) {
            if (type == delayType.getType()) {
                return delayType;
            }
        }
        return null;
    }
}
