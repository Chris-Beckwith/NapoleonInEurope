package game.client.political;

import game.controller.GameController;
import game.packet.political.WarDeclarationPacket;
import game.util.EventLogger;
import util.ServerResponseThread;

import java.util.ArrayList;

/**
 * WarDeclarationThread.java Date Created: 09 29, 2013
 *
 * Purpose: To tell the server that a nation has declared war on another nation.
 *
 * Description: There will be three types of war declarations:
 * 1) War declared on controlled nation.
 *      Tell the server so the server can tell all clients.
 *
 * 2) War declared on uncontrolled nation, when declarer is already at war.
 *      a) If only one User controls a nation at war with declarer, he takes control of uncontrolled nation.
 *      b) A User is chosen from the group at war with declarer to control uncontrolled nation.
 *
 * 3) War declared on uncontrolled nation, when declarer was not at war.
 *      Nations(Users) have a chance to declare war on declaring nation.
 *      A nation from this group is chosen to control nation (least amount of units)
 *      If no nations are at war with declarer after this choose from all players.
 *
 * @author Chris
 */
public class WarDeclarationThread  extends ServerResponseThread {
    public WarDeclarationThread(byte[] gameId, int declaringNation, int enemyNation, int papCost) {
        super(gameId);
        threadPacket = new WarDeclarationPacket(gameId, declaringNation, enemyNation, papCost);
        threadOpCode = threadPacket.getOpCode();
    }

    public WarDeclarationThread(byte[] gameId, int declaringNation, int enemyNation, int papCost, int nation) {
        this(gameId, declaringNation, enemyNation, papCost);
        threadPacket = new WarDeclarationPacket(gameId, declaringNation, enemyNation, papCost, nation);
        threadOpCode = threadPacket.getOpCode();
    }

    public WarDeclarationThread(byte[] gameId, int declaringNation, int enemyNation, int papCost, ArrayList<Integer> userNations) {
        this(gameId, declaringNation, enemyNation, papCost);
        threadPacket = new WarDeclarationPacket(gameId, declaringNation, enemyNation, papCost, userNations);
        threadOpCode = threadPacket.getOpCode();
    }

    public WarDeclarationThread(byte[] gameId, int declaringNation, int enemyNation, int papCost, boolean isAtWar) {
        this(gameId, declaringNation, enemyNation, papCost);
        threadPacket = new WarDeclarationPacket(gameId, declaringNation, enemyNation, papCost, isAtWar);
        threadOpCode = threadPacket.getOpCode();
    }

    public static void parseWarDeclaration(WarDeclarationPacket warDec, GameController controller) {
        int declaringNation = warDec.getDeclaringNation();
        int enemyNation = warDec.getEnemyNation();
        int papCost = warDec.getPapCost();
        int warDecType = warDec.getType();
        controller.setupDiplomaticRound(EventLogger.DECLARE_WAR, declaringNation, enemyNation);
        controller.doWarDeclaration(declaringNation, enemyNation, papCost);

        switch (warDecType) {
            case WarDeclarationPacket.CONTROLLED:
                System.out.println("War Declaration Type CONTROLLED");
                controller.nextDiplomatTurn();
                break;
            case WarDeclarationPacket.UNCONTROLLED_ATWAR:
                System.out.println("War Declaration Type UNCONTROLLED_ATWAR");

                if (warDec.getNumOfUsers() == 1)
                    controller.doControlNonPlayerNation(declaringNation, enemyNation, warDec.getNationToControl(), false);
                else
                    controller.controlNationRequest(declaringNation, enemyNation, warDec.getUserNations());

                break;
            case WarDeclarationPacket.UNCONTROLLED_NEUTRAL:
                System.out.println("War Declaration Type UNCONTROLLED_NEUTRAL");
                controller.allowNationToDeclareWar(declaringNation, enemyNation);
                break;
        }
    }
}
