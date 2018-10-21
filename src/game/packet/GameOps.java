package game.packet;

/**
 * GameOps.java  Date Created: Oct 30, 2012
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public enum GameOps {
    //Political Actions
    DECLARE_WAR       ( (byte) 0x20 ),
    SUE_FOR_PEACE     ( (byte) 0x21 ),
    ARMISTICE         ( (byte) 0x22 ),
    FORM_ALLIANCE     ( (byte) 0x23 ),
    BREAK_ALLIANCE    ( (byte) 0x24 ),
    RECRUIT_MINOR     ( (byte) 0x25 ),
    ANNEX_MINOR       ( (byte) 0x26 ),
    RESTORE_REGION    ( (byte) 0x27 ),
    ABANDON_REGION    ( (byte) 0x28 ),
    FOMENT_UPRISING   ( (byte) 0x29 ),
    SUPPRESS_UPRISING ( (byte) 0x2A ),
    GRANT_PASSAGE     ( (byte) 0x2B ),
    RESCIND_PASSAGE   ( (byte) 0x2C ),
    ENFORE_CS         ( (byte) 0x2D ),
    CONTROL_NPN       ( (byte) 0x2E ),
    RELEASE_NPN       ( (byte) 0x2F ),

    NATION_TO_CONTROL ( (byte) 0x30 ),
    MULTI_NATION      ( (byte) 0x31 ),
    RETALIATION_WAR   ( (byte) 0x32 ),
    NEXT_DIPLOMAT     ( (byte) 0x33 ),
    SETUP_DIPLOMAT    ( (byte) 0x34 ),
    FAIL_CONTROL_NPN  ( (byte) 0x35 ),
    CONGRESS_PASS     ( (byte) 0x36 ),
    CONGRESS_ANNEX    ( (byte) 0x37 ),
    CONGRESS_RESTORE  ( (byte) 0x38 ),
    CONGRESS_FREESERF ( (byte) 0x39 ),
    CONGRESS_MOVENPN  ( (byte) 0x3A ),

    GAME_INFO         ( (byte) 0x00 ),
    GAME_USERS        ( (byte) 0x01 ),
//    CREATE          ( (byte) 0x02 ),
//      JOIN            ( (byte) 0x03 );
    CHAT              ( (byte) 0x04 ),
    GAME_READY        ( (byte) 0x05 ),
    PLACE_UNITS       ( (byte) 0x06 ),
    UNITS_PLACED      ( (byte) 0x07 ),
    ADD_UNITS         ( (byte) 0x08 ),
    GAME_NOT_FOUND    ( (byte) 0x09 ),
    USER_NOT_FOUND    ( (byte) 0x10 ),

    FAILURE           ( (byte) 0xF0 );

    private final byte value;

    GameOps(byte value) {
            this.value = value;
    }

    public byte valueOf() {
            return value;
    }
}