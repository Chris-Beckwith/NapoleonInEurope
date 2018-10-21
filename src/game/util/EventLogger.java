package game.util;

import org.joda.time.DateTime;

import java.io.*;
import java.util.ArrayList;

import lobby.controller.Nation;

/**
 * EventLogger.java  Date Created: Nov 13, 2012
 *
 * Purpose: To save important game events in a file and retrieve those events when needed.
 *
 * Description: This files will read/write events to/from an event file.
 * The events will be the following:  War Declaration, Sue for Peace and the grace periods that are to follow,
 * Conclude Armistice, Form Alliance, Break Alliance, Recruit Minor Nation, Annex Minor Nation, Restore Region,
 * Abandon Region, Foment Uprising, Suppress Uprising, Successful Uprising, Rights of Passage, Rescind Rights,
 * Enforce Continental System, Control Non-Player Nation, Captured Capital, Liberated Capital, Annexed Capital,
 * Great Britain Production Point Give-Away, Naval Battle, Ports Attack, Major Battle, Failed Commitment Rolls,
 * Annex Region when sueing for peace, Free Serfs when sueing for peace, Elimination of a Nation.
 *
 * @author Chrisb
 */
public final class EventLogger {

    private static BufferedWriter eventOut;
    private static String fileName;
    private static String preName = "logs/game/events/";
    private static String extension = ".vnts";
    private static final String token = ";";

    //todo fix this filename stuff.  don't know what this issue is

    /*
     * Creates the initial event file.  Called as soon as the program starts.
     */
    public static void createEventFile(byte[] gameId) {
        try {
            do {
                fileName = DateTime.now().toString().split("T")[0]
                        + "-" + Math.abs(gameId[0]) + "-" + Math.abs(gameId[1])
                        + "-" + Math.abs(61 * DateTime.now().getMillisOfDay() % 100);
            } while (new File(preName + fileName + extension).exists());

            eventOut = new BufferedWriter(new FileWriter(preName + fileName + extension));
            eventOut.close();
        } catch (IOException e) {
            System.err.println("Error setting up game event file");
            e.printStackTrace();
        }
    }

    /**
     * Add a Game Start event.
     * @param month - month the game started.
     * @param year - year the game started.
     * @return a string representing the event.
     *
     * eg: Quotes?
     */
    public static String addGameStartEvent(int month, int year) {
        return writeEvent(GAME_START + token + month + token + year);
    }

    /**
     * Add a War event.
     * @param declaringNation - nation declaring the war.
     * @param enemyNation - nation war is being declared on.
     * @param month - month war declaration was made.
     * @param year - year war declaration was made.
     * @return a string representing the event.
     *
     * eg: September of 1824, France declares war on Great Britain.
     */
    public static String addWarEvent(int declaringNation, int enemyNation, int month, int year) {
        return writeEvent(DECLARE_WAR + token + month + token + year + token + declaringNation + token + enemyNation);
    }

    /**
     * Add a Sue for Peace event.
     * @param sueingNation - nationing sueing for peace.
     * @param warringNations - nations that were at war with sueing nation.
     * @param firstNations - nations that were first to declare war (usually just one).
     * @param secondNation - second nation to declare war.
     * @param month - month peace was sued for.
     * @param year - year peace was sued for.
     * @return a string representing the event.
     *
     * eg: October of 1824, France sues for peace from Great Britain, Prussia and Russia.
     *     Great Britain and Prussia received 2 PAPs, Russia received 1 PAP from spoils of war.
     */
    public static String addSueForPeaceEvent(int sueingNation, ArrayList<Integer> warringNations, ArrayList<Integer> firstNations, int secondNation,
                                             int month, int year) {
        String eventString = SUE_FOR_PEACE + token + month + token + year + token + sueingNation;
        for (int nation : warringNations)
            eventString += token + nation;
        eventString += token + Nation.NEUTRAL;
        for (int nation : firstNations)
            eventString += token + nation;
        eventString += token + Nation.NEUTRAL;
        if (secondNation > 0)
            eventString += token + secondNation;
        return writeEvent(eventString);
    }

    /**
     * Add an Armistice event.
     * @param nationOne - one nation agreeing to the armistice.
     * @param nationTwo - second nation agreeing to the armistice.
     * @param month - month armistice was made.
     * @param year - year armistice was made.
     * @return a string representing the event.
     *
     * eg: November of 1824, Austria and the Ottoman Empire agree to conclude an armistice.
     */
    public static String addArmisticeEvent(int nationOne, int nationTwo, int month, int year) {
        return writeEvent(CONCLUDE_ARMISTICE + token + month + token + year + token + nationOne + token + nationTwo);
    }

    /**
     * Add form Alliance event.
     * @param nationOne - one nation in the alliance.
     * @param nationTwo - second nation in the alliance.
     * @param month - month of the alliance.
     * @param year - year of the alliance.
     * @return a string representing the event.
     *
     * eg: December of 1824, Austria and the Ottoman Empire agree to form an alliance.
     */
    public static String addFormAllianceEvent(int nationOne, int nationTwo, int month, int year) {
        return writeEvent(FORM_ALLIANCE + token + month + token + year + token + nationOne + token + nationTwo);
    }

    /**
     * Add a break Alliance event.
     * @param breakingNation - nation breaking the alliance.
     * @param allyNation - nation breakingNation was allied with.
     * @param month - month alliance was ended.
     * @param year - year alliance was ended.
     * @return a string representing the event.
     *
     * eg: January of 1825, Austria ends their alliance with the Ottoman Empire.
     */
    public static String addBreakAllianceEvent(int breakingNation, int allyNation, int month, int year) {
        return writeEvent(BREAK_ALLIANCE + token + month + token + year + token + breakingNation + token + allyNation);
    }

    /**
     * Add Recuit Minor Nation event.
     * @param majorNation - nation doing the acquiring.
     * @param minorNation - nating being acquiried.
     * @param month - month nation was recruited.
     * @param year - year nation was recruited.
     * @return a string representing the event.
     *
     * eg: Feburary of 1825, Spain recruits the minor nation of Portugal.
     */
    public static String addRecruitMinorNationEvent(int majorNation, String minorNation, int month, int year) {
        return writeEvent(RECRUIT_MINOR + token + month + token + year + token + majorNation + token + minorNation);
    }

    /**
     * Add Annex Minor Nation event.
     * @param majorNation - nation doing the annexing.
     * @param minorNation - nation being annexed.
     * @param month - month nation was annexed.
     * @param year - year nation was annexed.
     * @return a string representing the event.
     *
     * eg: March of 1825, Spain annexes the minor nation of Gibraltar.
     */
    public static String addAnnexMinorNationEvent(int majorNation, String minorNation, int month, int year) {
        return writeEvent(ANNEX_MINOR + token + month + token + year + token + majorNation + token + minorNation);
    }

    /**
     * Add Restore Region event.
     * @param restoringNation - nation restoring the region.
     * @param region - region being restored.
     * @param owningNation - the previous owner of the region.
     * @param month - month region was restored.
     * @param year - year region was restored.
     * @return a string representing the event.
     *
     * eg: April of 1825, France restores Switzerland back to it's local government from Austrian control.
     */
    public static String addRestoreRegionEvent(int restoringNation, String region, int owningNation, int month, int year) {
        return writeEvent(RESTORE_REGION + token + month + token + year + token + restoringNation + token + region + token + owningNation);
    }

    /**
     * Add Abandon Region event.
     * @param majorNation - nation abandoning a region.
     * @param region - region being abandoned.
     * @param month - month region was abandoned.
     * @param year - year region was abandoned.
     * @return a string representing the event.
     *
     * eg: May of 1825, Prussia abandons the region of Holland.
     */
    public static String addAbandonRegionEvent(int majorNation, String region, int month, int year) {
        return writeEvent(ABANDON_REGION + token + month + token + year + token + majorNation + token + region);
    }

    /**
     * Add Foment Uprising event.
     * @param majorNation - nation who owns the region.
     * @param region - region in uprising.
     * @param month - month uprising started.
     * @param year - year uprising started.
     * @return a string representing the event.
     *
     * eg: June of 1825, funded by an unknown source, the British region of Ireland enters into political unrest.
     */
    public static String addFomentUprisingEvent(int majorNation, String region, int month, int year) {
        return writeEvent(FOMENT_UPRISING + token + month + token + year + token + majorNation + token + region);
    }

    /**
     * Add Suppress Uprising event.
     * @param majorNation - nation with region in uprising.
     * @param region - region in uprising.
     * @param month - month region was suppressed.
     * @param year - year region was suppressed.
     * @return a string representing the event.
     *
     * eg: July of 1825, British forces suppress the uprising in the region of Ireland.
     */
    public static String addSuppressUprisingEvent(int majorNation, String region, int month, int year) {
        return writeEvent(SUPPRESS_UPRISING + token + month + token + year + token + majorNation + token + region);
    }

    /**
     * Add Success Uprising event.
     * @param majorNation - nation with region in uprising.
     * @param region - region that successfully rebelled.
     * @param month - month region escaped majorNations control.
     * @param year - year region escaped majorNations control.
     * @return a string representing the event.
     *
     * eg: August of 1825, the region of Sicily successfully overthrew the occupying Austrian government.
     */
    public static String addSuccessUprisingEvent(int majorNation, String region, int month, int year) {
        return writeEvent(SUCCESS_UPRISING + token + month + token + year + token + majorNation + token + region);
    }

    /**
     * Add Right of Passage event.
     * @param grantingNation - nation granting rights.
     * @param receivingNation - nation receiving rights.
     * @param month - month rights were given.
     * @param year - year rights were given.
     * @return a string representing the event.
     *
     * eg: September of 1825, Austria grant Russia rights of passage through Austrian territory.
     */
    public static String addRightOfPassageEvent(int grantingNation, int receivingNation, int month, int year) {
        return writeEvent(GRANT_PASSAGE + token + month + token + year + token + grantingNation + token + receivingNation);
    }

    /**
     * Add Rescind Right of Passage event.
     * @param grantingNation - nation rescinding rights.
     * @param receivingNation - nation losing rights.
     * @param month - month rights were rescinded.
     * @param year - year rights were rescinded.
     * @return a string representing the event.
     *
     * eg: October of 1825, Austria rescind Russian rights of passage through Austrian territory.
     */
    public static String addRescindRightsEvent(int grantingNation, int receivingNation, int month, int year) {
        return writeEvent(RESCIND_PASSAGE + token + month + token + year + token + grantingNation + token + receivingNation);
    }

    /**
     * Add Continental System Enforced event.
     * @param month - month continental system was enforced.
     * @param year - year continental system was enforced.
     * @return a string representing the event.
     *
     * eg: November of 1825, France enforce the Continental System on Great Britain.
     */
    public static String addEnforceContinentalSystemEvent(int month, int year) {
        return writeEvent(ENFORCE_CS + token + month + token + year);
    }

    /**
     * Add Control non-Player Nation event.
     * @param userName - name of the user controlling the majorNation.
     * @param majorNation - nation gaining control of NPN.
     * @param nonPlayerNation - nation that is a being controlled.
     * @param month - month of the change in power.
     * @param year - year of the change in power.
     * @return a string representing the event.
     *
     * eg: December of 1825, 2blue of Austria gains control of the non-player nation, the Ottoman Empire.
     */
    public static String addControlNPNEvent(String userName, int majorNation, int nonPlayerNation, int month, int year) {
        return writeEvent(CONTROL_NPN + token + month + token + year + token + userName + token +  majorNation + token + nonPlayerNation);
    }
    /**
     * Add Release non-Player Nation event.
     * @param userName - name of the user controlling the majorNation.
     * @param majorNation - nation releasing control of NPN.
     * @param nonPlayerNation - nation that was controlled.
     * @param month - month of the change in power.
     * @param year - year of the change in power.
     * @return a string representing the event.
     *
     * eg: December of 1825, 2blue of Austria releases control of the non-player nation, the Ottoman Empire.
     */
    public static String addReleaseNPNEvent(String userName, int majorNation, int nonPlayerNation, int month, int year) {
        return writeEvent(RELEASE_NPN + token + month + token + year + token + userName + token +  majorNation + token + nonPlayerNation);
    }

    /**
     * Add Failed Control non-Player Nation event.
     * @param userName - name of the user controlling the majorNation.
     * @param majorNation - nation failed to gain control of NPN.
     * @param nonPlayerNation - nation that was failed to be controlled.
     * @param month - month of the failed change in power.
     * @param year - year of the failed change in power.
     * @return a string representing the event.
     *
     * eg: December of 1825, 2blue of Austria failed to control the non-player nation, the Ottoman Empire.
     */
    public static String addFailControlNPNEvent(String userName, int majorNation, int nonPlayerNation, int month, int year) {
        return writeEvent(FAILED_CONTROL_NPN + token + month + token + year + token + userName + token +  majorNation + token + nonPlayerNation);
    }

    /**
     * Add Capture Capital event.
     * @param cappingNation - nation that captured the capital.
     * @param capturedNation - nation whose capital was captured.
     * @param capital - name of the capital
     * @param month - month the capital was captured.
     * @param year - year the capital was captured.
     * @return a string representing the event.
     *
     * eg: January of 1826, Austria captures the Prussian capital, Berlin.
     */
    public static String addCaptureCapitalEvent(int cappingNation, int capturedNation, String capital, int month, int year) {
        return writeEvent(CAPTURE_CAPITAL + token + month + token + year + token + cappingNation + token + capturedNation + token + capital);
    }

    /**
     * Add Liberate Capital event.
     * @param majorNation - nation whose capital was liberated.
     * @param capital - name of the capital.
     * @param month - month the capital was liberated.
     * @param year - year the capital was liberated.
     * @return a string representing the event.
     *
     * eg: Feburary of 1826, Prussian forces liberate their capital, Berlin.
     */
    public static String addLiberatedCapitalEvent(int majorNation, String capital, int month, int year) {
        return writeEvent(LIBERATE_CAPITAL + token + month + token + year + token + majorNation + token + capital);
    }

    /**
     * Add Annex Capital Event.
     * @param annexingNation - nation who is annexing the capital.
     * @param sueingNation - nation whose capital is being annexed.
     * @param capital - name of the capital.
     * @param month - month capital was annexed.
     * @param year - year capital was annexed.
     * @return a string representing the event.
     *
     * eg: March of 1826, Austria annex the French capital, Paris.
     */
    public static String addAnnexCapitalEvent(int annexingNation, int sueingNation, String capital, int month, int year) {
        return writeEvent(ANNEX_CAPITAL + token + month + token + year + token + annexingNation + token + sueingNation + token + capital);
    }

    /**
     * Add Great Britain Production Point Give Away Event.
     * @param majorNation - nation receiving production points.
     * @param month - month production points were transfered.
     * @param year - year production points were transfered.
     * @return a string representing the event.
     *
     * eg: April of 1826, Great Britain gives production points to Austria.
     */
    public static String addGreatBritainPPGiveAway(int majorNation, int month, int year) {
        return writeEvent(GB_PP_GIVE_AWAY + token + month + token + year + token + majorNation);
    }

    /**
     * Add Naval Battle event.
     * @param victoryNations - nations on the winning side.
     * @param defeatedNations - nations on the losing side.
     * @param seaRegion - sea region battle was fought in.
     * @param month - month of naval battle.
     * @param year - year of naval battle.
     * @return a string representing the event.
     *
     * eg: May of 1826, French and Spanish combined naval forces defeat British naval forces in the English Channel.
     */
    public static String addNavalBattleEvent(int[] victoryNations, int[] defeatedNations, String seaRegion, int month, int year) {
        String eventString = NAVAL_BATTLE + token + month + token + year + token;
        for (int nation : victoryNations)
            eventString += nation;
        eventString += token;
        for (int nation : defeatedNations)
            eventString += nation;
        eventString += token + seaRegion;
        return writeEvent(eventString);
    }

    /**
     * Add Ports Attack event.
     * @param attackingNations - nations attacking the port.
     * @param defendingNations - nations defending the port.
     * @param port - name of port.
     * @param defended - was the attack defended or not.
     * @param month - month the port was attacked.
     * @param year - year the port was attacked.
     * @return a string representing the event.
     *
     * eg: June of 1826, Prussian and Russian combined naval forces defend an attack against the Ports of Brest from British naval forces.
     */
    public static String addPortAttackEvent(int[] attackingNations, int[] defendingNations, String port, boolean defended, int month, int year) {
        String eventString = PORT_ATTACK + token + month + token + year + token;
        for (int nation : attackingNations)
            eventString += nation;
        eventString += token;
        for (int nation : defendingNations)
            eventString += nation;
        eventString += token + (defended ? 1 : 0) + token + port;
        return writeEvent(eventString);
    }

    /**
     * Add Major Battle event.
     * @param victoryNations - nations that were victorious in battle.
     * @param defeatedNations - nations that were defeated in battle.
     * @param region - region the battle took place.
     * @param month - month battle took place.
     * @param year - year battle took place.
     * @return a string representing the event.
     *
     * eg: July of 1826, Austrian forces defeat Prussian and Russian combined forces in a major battle at Bohemia.
     */
    public static String addMajorBattleEvent(int[] victoryNations, int[] defeatedNations, String region, int month, int year) {
        String eventString = MAJOR_BATTLE + token + month + token + year + token;
        for (int nation : victoryNations)
            eventString += nation;
        eventString += token;
        for (int nation : defeatedNations)
            eventString += nation;
        eventString += token + region;
        return writeEvent(eventString);
    }

    /**
     * Add Fail Commitment event.
     * @param nation - nation failing the commitment check.
     * @param month - month of failed commitment.
     * @param year - year of failed commitment.
     * @return a string representing the event.
     *
     * eg: August of 1826, Russia fails to make their commitment check.
     */
    public static String addFailCommitmentEvent(int nation, int month, int year) {
        return writeEvent(FAILED_COMMITMENT + token + month + token + year + token + nation);
    }

    /**
     * Add Annex Region event.  Occurs when a nation sues for peace.
     * @param congressNation - nation annexing region.
     * @param sueingNation - nationg that lost the war.
     * @param isHomeland - specifies if the region is a native-owned homeland region.
     * @param region - region annexed.
     * @param month - month region was annexed.
     * @param year - year region was annexed.
     * @return a string representing the event.
     *
     * eg: September of 1826, France annexes the Prussian owned region of Hanover.
     */
    public static String addAnnexRegionEvent(int congressNation, int sueingNation, boolean isHomeland, String region, int month, int year) {
        String eventString = ANNEX_REGION + token + month + token + year;
        eventString += token + congressNation + token + sueingNation;
        eventString += token + (isHomeland ? 1 : 0) + token + region;
        return writeEvent(eventString);
    }

    /**
     * Add Free Serfs event.
     * @param congressNation - nation freeing serfs.
     * @param region - region freed.
     * @param month - month regions were freed.
     * @param year - year regions were freed.
     * @return a string representing the event.
     *
     * eg: November of 1826, The Ottoman Empire free the Russian Serfs in the region of Crimea.
     */
    public static String addFreeSerfsEvent(int congressNation, String region, int month, int year) {
        return writeEvent(FREE_SERFS + token + month + token + year + token + congressNation + token + region);
    }

    /**
     * Add Nation Eliminated Event.
     * @param majorNation - the nation that was eliminated.
     * @param month - month the nation was eliminated.
     * @param year - year the nation was eliminated.
     * @return a string representing the event.
     *
     * eg: May of 1826, The Ottoman Empire has been eliminated.
     */
    public static String addNationEliminatedEvent(int majorNation, int month, int year) {
        return writeEvent(NATION_ELIMINATED + token + month + token + year + token + majorNation);
    }

    /**
     * The method is currently only used to display the events in the eventLogMenu.
     * @param eventFileName - The file name of the events file you which to read from.
     * @return The events output as a String with new lines in between each event.
     */
    public static String getEvents(String eventFileName) {
        if (new File(eventFileName).exists()) {
            try {
                BufferedReader eventIn = new BufferedReader(new FileReader(eventFileName));
                String events = "";
                String lineIn;

                while ( (lineIn = eventIn.readLine()) != null) {
                    String[] args = lineIn.split(token);
                    if (events.length() != 0)
                        events += "\n";

                    System.out.print("args: ");
                    for (String s : args)
                        System.out.print(s + " ");
                    System.out.println("");

                    //Get Event As String
                    if ( args[0].length() >  0 ) {
                        //Pre-event string
                        events += " -- ";
                        events += parseEvent(args);
                    }
                }
                return events;
            } catch (FileNotFoundException e) {
                GameLogger.log("EventLogger:getEvents - " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                GameLogger.log("EventLogger:getEvents - " + e.getMessage());
                e.printStackTrace();
            }
            return "This should only show if an error occurred.";
        } else {
            return "Could not locate the events log: " + eventFileName;
        }
    }

    public static String parseEvent(String eventString) {
        return parseEvent(eventString.split(token));
    }

    public static String parseEvent(String[] args) {
        String event = "";
        try {
            if (Integer.parseInt(args[0]) != GAME_START)
                event += monthOfYear(args[1],args[2]);

            switch (Integer.parseInt(args[0])) {
                case GAME_START: event = GameMsg.getString("event.gameStart.1"); break;
                case DECLARE_WAR: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.war") + " " + GameMsg.getString("nation." + args[4]) + ".";
                    break;
                case SUE_FOR_PEACE: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.sue") +  " " + GameMsg.getString("nation." + args[4]);

                    int zeroOne = -1;
                    int zeroTwo = -1;
                    for (int i = 5; i < args.length - 1; i++) {
                        if (Integer.parseInt(args[i]) == Nation.NEUTRAL) {
                            if (zeroOne > 0)
                                zeroTwo = i;
                            else
                                zeroOne = i;
                        }
                    }
                    if (zeroOne > 5) {
                        for (int j = 5; j < zeroOne - 1; j++)
                            event += ", " + GameMsg.getString("nation." + args[j]);

                        event += " and " + GameMsg.getString("nation." + args[zeroOne - 1]);
                    }
                    event += ".\n";

                    //Spoils
                    //Great Britain and Prussia received 2 PAPs, Russia received 1 PAP from spoils of war.
                    event += GameMsg.getString("nation." + args[zeroOne + 1]);
                    if (zeroTwo > zeroOne + 2) {
                        for (int k = zeroOne + 1; k < zeroTwo - 1; k++)
                            event += ", " + GameMsg.getString("nation." + args[k]);

                        event += " and " + GameMsg.getString("nation." + args[zeroTwo - 1]);
                    }
                    event += GameMsg.getString("event.sue.spoils.1"); //receives 2 PAPs,
                    event += " " + GameMsg.getString("nation." + args[args.length - 1]);
                    event += " " + GameMsg.getString("event.sue.spoils.2"); //from spoils of war.
                    break;
                case CONCLUDE_ARMISTICE: event += GameMsg.getString("nation." + args[3]) + " and "
                        + GameMsg.getString("nation." + args[4]) + " " + GameMsg.getString("event.arm");
                    break;
                case FORM_ALLIANCE: event += GameMsg.getString("nation." + args[3]) + " and "
                        + GameMsg.getString("nation." + args[4]) + " " + GameMsg.getString("event.ally");
                    break;
                case BREAK_ALLIANCE: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.break") + " " + GameMsg.getString("nation." + args[4]) + ".";
                    break;
                case RECRUIT_MINOR: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.recruit") + " " + args[4] + ".";
                    break;
                case ANNEX_MINOR: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.annex") + " " + args[4] + ".";
                    break;
                case RESTORE_REGION: event += GameMsg.getString("nation." + args[3]) + " restores " + args[4] + " "
                        + GameMsg.getString("event.restore") + " " + GameMsg.getString("nation.pos." + args[5])
                        + " control.";
                    break;
                case ABANDON_REGION: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.abandon") + " " + args[4] + ".";
                    break;
                case FOMENT_UPRISING: event += GameMsg.getString("event.foment.1") + " "
                        + GameMsg.getString("nation.pos." + args[3]) + " region of " + args[4] + " "
                        + GameMsg.getString("event.formet.2");
                    break;
                case SUPPRESS_UPRISING: event += GameMsg.getString("nation.pos." + args[3]) + " "
                        + GameMsg.getString("event.suppress") + " " + args[4] + ".";
                    break;
                case SUCCESS_UPRISING: event += "the region of " + args[4] + " "
                        + GameMsg.getString("event.success") + " " + GameMsg.getString("nation.pos." + args[3])
                        + " government.";
                    break;
                case GRANT_PASSAGE: event += GameMsg.getString("nation." + args[3]) + " grants "
                        + GameMsg.getString("nation." + args[4]) + " " + GameMsg.getString("event.rights") + " "
                        + GameMsg.getString("nation.pos." + args[3]) + " territory.";
                    break;
                case RESCIND_PASSAGE: event += GameMsg.getString("nation." + args[3]) + " rescinds "
                        + GameMsg.getString("nation.pos." + args[4]) + " " + GameMsg.getString("event.rights")
                        + " " + GameMsg.getString("nation.pos." + args[3]) + " territory.";
                    break;
                case ENFORCE_CS: event += GameMsg.getString("event.enforce");
                    break;
                case CONTROL_NPN: event += args[3] + " of " + GameMsg.getString("nation." + args[4]) + " "
                        + GameMsg.getString("event.control") + " " + GameMsg.getString("nation." + args[5]) + ".";
                    break;
                case RELEASE_NPN: event += args[3] + " of " + GameMsg.getString("nation." + args[4]) + " "
                        + GameMsg.getString("event.release") + " " + GameMsg.getString("nation." + args[5]) + ".";
                    break;
                case FAILED_CONTROL_NPN: event += args[3] + " of " + GameMsg.getString("nation." + args[4]) + " "
                        + GameMsg.getString("event.failControl") + " " + GameMsg.getString("nation." + args[5]) + ".";
                    break;
                case CAPTURE_CAPITAL: event += GameMsg.getString("nation." + args[3]) + " captures the "
                        + GameMsg.getString("nation.pos." + args[4]) + " capital, " + args[5] + ".";
                    break;
                case LIBERATE_CAPITAL: event += GameMsg.getString("nation.pos." + args[3]) + " "
                        + GameMsg.getString("event.liberate") + " " + args[4] + ".";
                    break;
                case ANNEX_CAPITAL: event += GameMsg.getString("nation." + args[3]) + " annex the "
                        + GameMsg.getString("nation." + args[4]) + " capital, " + args[5] + ".";
                    break;
                case GB_PP_GIVE_AWAY: event += GameMsg.getString("event.ppGiveAway") + " "
                        + GameMsg.getString("nation." + args[3]) + ".";
                    break;
                case NAVAL_BATTLE:
                    char[] vNations =  args[3].toCharArray();
                    event += GameMsg.getString("nation.pos." + vNations[0]);

                    for (int i = 1; i < vNations.length - 1; i++)
                        event += ", " + GameMsg.getString("nation.pos." + vNations[i]);
                    if (vNations.length > 1)
                        event += " and " + GameMsg.getString("nation.pos." + vNations[vNations.length-1])
                                + " combined";
                    event += " naval forces defeat ";

                    char[] dNations =  args[4].toCharArray();
                    event += GameMsg.getString("nation.pos." + dNations[0]);

                    for (int i = 1; i < dNations.length - 1; i++)
                        event += ", " + GameMsg.getString("nation.pos." + dNations[i]);
                    if (dNations.length > 1)
                        event += " and " + GameMsg.getString("nation.pos." + dNations[dNations.length-1])
                                + " combined";
                    event += " naval forces in the " + args[5] + ".";
                    break;
                case PORT_ATTACK:
                    char[] aNations =  args[3].toCharArray();
                    event += GameMsg.getString("nation.pos." + aNations[0]);

                    for (int i = 1; i < aNations.length - 1; i++)
                        event += ", " + GameMsg.getString("nation.pos." + aNations[i]);
                    if (aNations.length > 1)
                        event += " and " + GameMsg.getString("nation.pos." + aNations[aNations.length-1])
                                + " combined";
                    event += " " + GameMsg.getString("event.port." + args[5]) + " " + args[6] + " from ";

                    char[] pNations =  args[4].toCharArray();
                    event += GameMsg.getString("nation.pos." + pNations[0]);

                    for (int i = 1; i < pNations.length - 1; i++)
                        event += ", " + GameMsg.getString("nation.pos." + pNations[i]);
                    if (pNations.length > 1)
                        event += " and " + GameMsg.getString("nation.pos." + pNations[pNations.length-1])
                                + " combined";
                    event += " naval forces.";
                    break;
                case MAJOR_BATTLE:
                    char[] wNations =  args[3].toCharArray();
                    event += GameMsg.getString("nation.pos." + wNations[0]);

                    for (int i = 1; i < wNations.length - 1; i++)
                        event += ", " + GameMsg.getString("nation.pos." + wNations[i]);
                    if (wNations.length > 1)
                        event += " and " + GameMsg.getString("nation.pos." + wNations[wNations.length-1])
                                + " combined";
                    event += " forces defeat ";

                    char[] lNations =  args[4].toCharArray();
                    event += GameMsg.getString("nation.pos." + lNations[0]);

                    for (int i = 1; i < lNations.length - 1; i++)
                        event += ", " + GameMsg.getString("nation.pos." + lNations[i]);
                    if (lNations.length > 1)
                        event += " and " + GameMsg.getString("nation.pos." + lNations[lNations.length-1])
                                + " combined";
                    event += " " + GameMsg.getString("event.battle") + " " + args[5] + ".";
                    break;
                case FAILED_COMMITMENT: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.fail");
                    break;
                case ANNEX_REGION: event += GameMsg.getString("nation." + args[3]) + " annexes the "
                        + GameMsg.getString("nation.pos." + args[4]) + " "
                        + GameMsg.getString("event.annexRegion." + args[5]) + " " + args[6] + ".";
                    break;
                case FREE_SERFS: event += GameMsg.getString("nation." + args[3]) + " "
                        + GameMsg.getString("event.free") + " " + args[4] + ".";
                    break;
                case NATION_ELIMINATED: event += GameMsg.getString("nation." + args[3])
                        + GameMsg.getString("event.eliminate");
                default: event += "Event Type unknown.";
            }
        } catch (IndexOutOfBoundsException e) {
            GameLogger.log("EventLogger:parseEvent - Index out of bounds - " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            GameLogger.log("EventLogger:parseEvent - Unparseable event line - " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("EventLogger: Event returned: " + event);
        return event;
    }

    private static String monthOfYear(String month, String year) {
        return GameMsg.getString("month.full." + month) + " of " + year + ", ";
    }

    private static String writeEvent(String eventString) {
        try {
            eventOut = new BufferedWriter(new FileWriter(getEventFileName(), true));
            eventOut.write(System.getProperty("line.separator") + eventString);
            eventOut.close();
        } catch (IOException e) {
            System.err.println("Error writing to game event file");
            e.printStackTrace();
        }
        return eventString;
    }

    public static String getEventFileName() { return preName + fileName + extension; }

    //Political Actions
    public static final int DECLARE_WAR         = 0;
    public static final int SUE_FOR_PEACE       = 1;
    public static final int CONCLUDE_ARMISTICE  = 2;
    public static final int FORM_ALLIANCE       = 3;
    public static final int BREAK_ALLIANCE      = 4;
    public static final int RECRUIT_MINOR       = 5;
    public static final int ANNEX_MINOR         = 6;
    public static final int RESTORE_REGION      = 7;
    public static final int ABANDON_REGION      = 8;
    public static final int FOMENT_UPRISING     = 9;
    public static final int SUPPRESS_UPRISING   = 10;
    public static final int GRANT_PASSAGE       = 11;
    public static final int RESCIND_PASSAGE     = 12;
    public static final int ENFORCE_CS          = 13;
    public static final int CONTROL_NPN         = 14;
    public static final int RELEASE_NPN         = 15;

    //Events
    public static final int FAILED_CONTROL_NPN  = 16;
    public static final int SUCCESS_UPRISING    = 17;
    public static final int CAPTURE_CAPITAL     = 18;
    public static final int LIBERATE_CAPITAL    = 19;
    public static final int ANNEX_CAPITAL       = 20;
    public static final int GB_PP_GIVE_AWAY     = 21;
    public static final int NAVAL_BATTLE        = 22;
    public static final int PORT_ATTACK         = 23;
    public static final int MAJOR_BATTLE        = 24;
    public static final int FAILED_COMMITMENT   = 25;
    public static final int ANNEX_REGION        = 26;
    public static final int FREE_SERFS          = 27;
    public static final int NATION_ELIMINATED   = 28;

    public static final int GAME_START          = 29;
}