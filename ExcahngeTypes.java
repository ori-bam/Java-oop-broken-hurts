
enum ExchangeTypes {
    LEFT, RIGHT, ACROSS, NONE;

    private static final ExchangeTypes[] values = ExchangeTypes.values();
    public static final int NUM_TYPES = values.length;

    public static ExchangeTypes fromOrdinal(int param) {
        return values[param % NUM_TYPES];
    }

    public static int direction(ExchangeTypes type) {// figure out the direction needed for exchange
        switch (type) {
            case LEFT:
                return 1;
            case RIGHT:
                return ExchangeTypes.NUM_TYPES - 1;
            case ACROSS:
                return 2;
            case NONE:
                return 0;

        }
        return 0;
    }

}
