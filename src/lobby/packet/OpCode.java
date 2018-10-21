package lobby.packet;

/**
 * OpCode.java  Date Created:
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public enum OpCode {
    SET_NATION      ( (byte) 0x8f ),
    NATION_DESC     ( (byte) 0x90 ),
    GETLOBBYS       ( (byte) 0x91 ),
    MORELOBBYS      ( (byte) 0x92 ),
    SET_OPTION      ( (byte) 0x93 ),
    MAKE_READY      ( (byte) 0x94 ),
    JOIN_SETTINGS   ( (byte) 0x95 ),
    COUNTDOWN       ( (byte) 0x96 ),
    STARTING        ( (byte) 0x97 ),
    START_GAME      ( (byte) 0x98 ),
    LOBBY_NOT_READY ( (byte) 0x99 ),
//    REQUESTUSERS	( (byte) 0xA0 ),
//    READY           ( (byte) 0xA1 ),
//    UNREADY         ( (byte) 0xA2 ),
    EMPTY           ( (byte) 0xFE ),
    LAST            ( (byte) 0xF0 ),

    LOGIN           ( (byte) 0x00 ),
    LOGOUT          ( (byte) 0x01 ),
    CREATE          ( (byte) 0x02 ),
    JOIN            ( (byte) 0x03 ),
    CHAT            ( (byte) 0x04 ),
    LOBBY_FULL      ( (byte) 0x05 ),
    LOBBY_EXISTS    ( (byte) 0x06 ),
    USER_EXISTS     ( (byte) 0x07 ),
    STOP            ( (byte) 0x08 ),
    LEAVE_LOBBY     ( (byte) 0x09 ),
    FAILURE         ( (byte) 0x0a );

    private final byte value;

    OpCode(byte value) {
            this.value = value;
    }

    public byte valueOf() {
            return value;
    }
}
