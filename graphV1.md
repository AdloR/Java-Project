```plantuml

@startuml
!theme plain
top to bottom direction
skinparam linetype ortho

class manage.AdvancedFireFighterChief {
  + affectRobot(simu.Simulateur): void
}
class terrain.Carte {
  + voisinExiste(terrain.Case, terrain.Direction): boolean
  + getVoisin(terrain.Case, terrain.Direction): terrain.Case
  + getCase(int, int): terrain.Case
  + getVoisins(terrain.Case): ArrayList<terrain.Case>
  + getTailleCases(): int
  + getdir(terrain.Case, terrain.Case): terrain.Direction
  + getNbColonnes(): int
  + getCases(): Iterable<terrain.Case>
  + setCase(terrain.Case): void
  + getNbLignes(): int
}
class terrain.Case {
  + getType(): terrain.NatureTerrain
  + setIncendie(simu.Incendie): void
  + getterrain.Carte(): terrain.Carte
  + getLigne(): int
  + getIncendie(): simu.Incendie
  + getColonne(): int
}
class simu.DebInterventionEven {
  + execute(): void
}
class simu.DebRemplissageEven {
  + execute(): void
}
class simu.DebRobotBougeEven {
  + execute(): void
}
enum terrain.Direction << enumeration >> {
  + EST:
  + SUD:
  + OUEST:
  + NORD:
  + values(): terrain.Direction[]
  + valueOf(String): terrain.Direction
}
class simu.DonneesSimulation {
  + getFichierDonnees(): String
  + getRobots(): List<robot.Robot>
  + getIncendies(): List<simu.Incendie>
  + getterrain.Carte(): terrain.Carte
}
class robot.Robot.Drone {
  + findWater(terrain.Case): boolean
  + setSpeed(int): void
  + isAccessible(terrain.Case): boolean
}
class manage.ElementaryFirefighterChief {
  + affectRobot(simu.Simulateur): void
}
class simu.Evenement {
  + isPriority(): boolean
  + isAuto(): boolean
  + getDate(): long
  + execute(): void
  + compareTo(simu.Evenement): int
}
class manage.FireFighterChief {
  + affectRobot(simu.Simulateur): void
}
class exceptions.ForbiddenMoveException
class manage.ImprovedFirefighterChief {
  + CondIncendies(terrain.Case): boolean
  + affectRobot(simu.Simulateur): void
}
class simu.Incendie {
  + getFireCase(): terrain.Case
  + setFireCase(terrain.Case): void
  + setNbL(int): void
  + toString(): String
  + getNbL(): int
}
class Inondation {
  ~ main(String[]): void
}
class simu.InterventionEven {
  + execute(): void
}
class Invader {
  ~ next(): void
  ~ restart(): void
}
class simu.LancementStrategie {
  + execute(): void
}
class io.LecteurDonnees {
  + lire(String): simu.DonneesSimulation
}
enum terrain.NatureTerrain << enumeration >> {
  + TERRAIN_LIBRE:
  + FORET:
  + ROCHE:
  + HABITAT:
  + EAU:
  + valueOf(String): terrain.NatureTerrain
  + values(): terrain.NatureTerrain[]
}
class exceptions.NotNeighboringCasesException
class pathfinding.Path {
  + getterrain.Carte(): terrain.Carte
  + addStep(terrain.Case): void
  + getStart(): terrain.Case
  + getDuration(): int
  + getPath(): LinkedList<terrain.Direction>
}
class simu.RemplissageEven {
  + execute(): void
}
class robot.Robot {
  + getReservoir(): int
  + isAccessible(terrain.Case): boolean
  + followPath(simu.Simulateur, pathfinding.Path): void
  + setPosition(terrain.Case): void
  + deverserEau(): int
  + startMove(simu.Simulateur, terrain.Direction): void
  + isWaiting(simu.Simulateur): boolean
  + startMove(simu.Simulateur, terrain.Direction, terrain.Case): void
  + getTimeOn(terrain.Case): int
  + remplirReservoir(): void
  + startIntervention(simu.Simulateur, boolean): void
  + remplir(simu.Simulateur): void
  + getPosition(): terrain.Case
  + intervenir(simu.Simulateur, long, boolean): void
  + getSpeedOn(terrain.Case): int
  + remplir(simu.Simulateur, long): void
  + setSpeed(int): void
}
class robot.Robot.RobotAChenille {
  + setPosition(terrain.Case): void
  + isAccessible(terrain.Case): boolean
  + findWater(terrain.Case): boolean
  + getSpeedOn(terrain.Case): int
  + setSpeed(int): void
}
class robot.Robot.RobotAPattes {
  + findWater(terrain.Case): boolean
  + getSpeedOn(terrain.Case): int
  + setPosition(terrain.Case): void
  + isAccessible(terrain.Case): boolean
  + remplirReservoir(): void
  + getReservoir(): int
  + deverserEau(): int
}
class robot.Robot.RobotARoues {
  + findWater(terrain.Case): boolean
  + isAccessible(terrain.Case): boolean
  + setPosition(terrain.Case): void
}
class simu.RobotBougeEven {
  + execute(): void
}
class simu.RobotEven {
  + actualiserRobots(): void
}
class simu.RobotTeleportEven {
  + execute(): void
}
class Scenario0 {
  ~ main(String[]): void
}
class Scenario1 {
  ~ main(String[]): void
}
class pathfinding.SelfDriving {
  + getSpeedOn(terrain.Case): int
  + findWater(terrain.Case): boolean
  + getTimeOn(terrain.Case): int
  + Dijkstra(terrain.Case, CaseCompareCond): pathfinding.Path
  + isAccessible(terrain.Case): boolean
}
class simu.Simulateur {
  + largeur_tuiles: int
  + restart(): void
  + getDateSimulation(): long
  + getDonnees(): simu.DonneesSimulation
  + incrementeDate(): void
  + draw(): void
  + ajouteEvenement(simu.Evenement): void
  + next(): void
  + simulationTerminee(): boolean
}
class StrategieAvancee {
  ~ main(String[]): void
}
class StrategieElementaire {
  ~ main(String[]): void
}
class StrategieMieux {
  ~ main(String[]): void
}
class TestInvader {
  + main(String[]): void
}
class Testio.LecteurDonnees {
  + main(String[]): void
}
class exceptions.UnreachableCaseException

' Heritage relationships

' ## FireFighterChiefs
manage.AdvancedFireFighterChief   -[#000082,plain]-^  manage.FireFighterChief
manage.ElementaryFirefighterChief -[#000082,plain]-^  manage.FireFighterChief
manage.ImprovedFirefighterChief   -[#000082,plain]-^  manage.FireFighterChief

' ## simu.Evenement
simu.LancementStrategie        -[#000082,plain]-^  simu.Evenement
simu.RemplissageEven           -[#000082,plain]-^  simu.Evenement
simu.RobotTeleportEven         -[#000082,plain]-^  simu.Evenement
simu.RobotEven                 -[#000082,plain]-^  simu.Evenement
' ### simu.RobotEven
simu.DebInterventionEven       -[#000082,plain]-^  simu.RobotEven
simu.DebRemplissageEven        -[#000082,plain]-^  simu.RobotEven
simu.DebRobotBougeEven         -[#000082,plain]-^  simu.RobotEven
simu.InterventionEven          -[#000082,plain]-^  simu.RobotEven
simu.RobotBougeEven            -[#000082,plain]-^  simu.RobotEven

' ## pathfinding.SelfDriving
robot.Robot                    -[#000082,plain]-^  pathfinding.SelfDriving
' ### robot.Robot
robot.Robot.Drone              -[#000082,plain]-^  robot.Robot
robot.Robot.RobotAChenille     -[#000082,plain]-^  robot.Robot
robot.Robot.RobotAPattes       -[#000082,plain]-^  robot.Robot
robot.Robot.RobotARoues        -[#000082,plain]-^  robot.Robot


' Agregations and compositions

' ## simu.DonneesSimulation
simu.DonneesSimulation    "1" *-[#595959,plain]-> "terrain.Carte\n1" terrain.Carte
simu.DonneesSimulation    "1" *-[#595959,plain]-> "incendies\n*" simu.Incendie
simu.DonneesSimulation    "1" *-[#595959,plain]-> "robots\n*" robot.Robot

terrain.Carte             "1" *-[#595959,plain]-> "cases\n*" terrain.Case

terrain.Case              "1" *-[#595959,plain]-> "simu.Incendie\n1" simu.Incendie
terrain.Case              "1" o-[#595959,plain]-> "type\n1" terrain.NatureTerrain

simu.Incendie             "1" o-[#595959,plain]-> "fireCase\n1" terrain.Case

simu.LancementStrategie   "1" o-[#595959,plain]-> "chief\n1" manage.FireFighterChief
simu.LancementStrategie   "1" o-[#595959,plain]-> "sim\n1" simu.Simulateur

pathfinding.Path          "1" o-[#595959,plain]-> "terrain.Carte\n1" terrain.Carte
pathfinding.Path          "1" o-[#595959,plain]-> "robot.Robot\n1" pathfinding.SelfDriving

simu.RemplissageEven      "1" o-[#595959,plain]-> "robot.Robot\n1" robot.Robot

robot.Robot               "1" o-[#595959,plain]-> "position\n1" terrain.Case

simu.RobotBougeEven       "1" o-[#595959,plain]-> "dir\n1" terrain.Direction
simu.RobotBougeEven       "1" o-[#595959,plain]-> "simu\n1" simu.Simulateur

simu.RobotEven            "1" o-[#595959,plain]-> "robot.Robot\n1" robot.Robot
simu.RobotEven            "1" o-[#595959,plain]-> "sim\n1" simu.Simulateur

simu.RobotTeleportEven    "1" o-[#595959,plain]-> "dest\n1" terrain.Case
simu.RobotTeleportEven    "1" o-[#595959,plain]-> "robot.Robot\n1" robot.Robot
simu.RobotTeleportEven    "1" o-[#595959,plain]-> "sim\n1" simu.Simulateur

simu.Simulateur           "1" o-[#595959,plain]-> "donnees\n1" simu.DonneesSimulation
simu.Simulateur           "1" *-[#595959,plain]-> "history\n*" simu.Evenement

' Reference Relationship
Inondation                    --[#595959,dashed]->  simu.Simulateur                         : "«create»"

io.LecteurDonnees             --[#595959,dashed]->  terrain.Carte                           : "«create»"
io.LecteurDonnees             --[#595959,dashed]->  terrain.Case                            : "«create»"
io.LecteurDonnees             --[#595959,dashed]->  simu.DonneesSimulation                  : "«create»"
io.LecteurDonnees             --[#595959,dashed]->  robot.Robot.Drone                       : "«create»"
io.LecteurDonnees             --[#595959,dashed]->  simu.Incendie                           : "«create»"
io.LecteurDonnees             --[#595959,dashed]->  robot.Robot.RobotAChenille              : "«create»"
io.LecteurDonnees             --[#595959,dashed]->  robot.Robot.RobotAPattes                : "«create»"
io.LecteurDonnees             --[#595959,dashed]->  robot.Robot.RobotARoues                 : "«create»"

pathfinding.Path              --[#595959,dashed]->  exceptions.NotNeighboringCasesException : "«create»"

robot.Robot                   --[#595959,dashed]->  simu.InterventionEven                   : "«create»"
robot.Robot                   --[#595959,dashed]->  simu.RemplissageEven                    : "«create»"
robot.Robot                   --[#595959,dashed]->  simu.RobotBougeEven                     : "«create»"

robot.Robot.RobotAChenille    --[#595959,dashed]->  exceptions.ForbiddenMoveException       : "«create»"

robot.Robot.RobotAPattes      --[#595959,dashed]->  exceptions.ForbiddenMoveException       : "«create»"

robot.Robot.RobotARoues       --[#595959,dashed]->  exceptions.ForbiddenMoveException       : "«create»"

Scenario0                     --[#595959,dashed]->  simu.DebRobotBougeEven                  : "«create»"
Scenario0                     --[#595959,dashed]->  simu.Simulateur                         : "«create»"

Scenario1                     --[#595959,dashed]->  simu.DebInterventionEven                : "«create»"
Scenario1                     --[#595959,dashed]->  simu.DebRemplissageEven                 : "«create»"
Scenario1                     --[#595959,dashed]->  simu.DebRobotBougeEven                  : "«create»"
Scenario1                     --[#595959,dashed]->  simu.Simulateur                         : "«create»"

pathfinding.SelfDriving       --[#595959,dashed]->  pathfinding.Path                        : "«create»"
pathfinding.SelfDriving       --[#595959,dashed]->  exceptions.UnreachableCaseException     : "«create»"

StrategieAvancee              --[#595959,dashed]->  manage.AdvancedFireFighterChief         : "«create»"
StrategieAvancee              --[#595959,dashed]->  simu.LancementStrategie                 : "«create»"
StrategieAvancee              --[#595959,dashed]->  simu.Simulateur                         : "«create»"

StrategieElementaire          --[#595959,dashed]->  manage.ElementaryFirefighterChief       : "«create»"
StrategieElementaire          --[#595959,dashed]->  simu.LancementStrategie                 : "«create»"
StrategieElementaire          --[#595959,dashed]->  simu.Simulateur                         : "«create»"

StrategieMieux                --[#595959,dashed]->  manage.ImprovedFirefighterChief         : "«create»"
StrategieMieux                --[#595959,dashed]->  simu.LancementStrategie                 : "«create»"
StrategieMieux                --[#595959,dashed]->  simu.Simulateur                         : "«create»"

TestInvader                   --[#595959,dashed]->  Invader                                 : "«create»"

@enduml
```