```plantuml
@startuml
left to right direction
skinparam linetype ortho


package simu {
  class DonneesSimulation {
    + getFichierDonnees(): String
    + getRobots(): List<Robot>
    + getIncendies(): List<Incendie>
    + getCarte(): Carte
  }
  class Incendie {
    + getFireCase(): Case
    + setFireCase(Case): void
    + setNbL(int): void
    + toString(): String
    + getNbL(): int
  }
  class Simulateur {
    + largeur_tuiles: int
    + restart(): void
    + getDateSimulation(): long
    + getDonnees(): DonneesSimulation
    + incrementeDate(): void
    + draw(): void
    + ajouteEvenement(Evenement): void
    + next(): void
    + simulationTerminee(): boolean
  }
  package evenements {
    abstract Evenement {
      + isPriority(): boolean
      + isAuto(): boolean
      + getDate(): long
      + execute(): void
      + compareTo(Evenement): int
    }
    class LancementStrategie {
      + execute(): void
    }
    package RobotEven {
      abstract RobotEven {
        + actualiserRobots(): void
      }
      package "" as ActionEven <<rectangle>> {
        class InterventionEven {
          + execute(): void
        }
        class RemplissageEven {
          + execute(): void
        }
        class RobotBougeEven {
          + execute(): void
        }
      }
      package "" as DebEven <<rectangle>> {
        class DebInterventionEven {
          + execute(): void
        }
        class DebRemplissageEven {
          + execute(): void
        }
        class DebRobotBougeEven {
          + execute(): void
        }
      }
      class RobotTeleportEven {
        + execute(): void
      }
    }
  }
}


package scenarios {
  class Inondation {
    ~ main(String[]): void
  }
  class Scenario0 {
    ~ main(String[]): void
  }
  class Scenario1 {
    ~ main(String[]): void
  }
}


package Strategie <<rectangle>> {
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
}


class Invader {
  ~ next(): void
  ~ restart(): void
}



package io {
  class LecteurDonnees {
    + lire(String): DonneesSimulation
  }
}


package manage {
  package "" as FireFighterChiefType <<rectangle>> {
    class ImprovedFirefighterChief {
      + CondIncendies(Case): boolean
      + affectRobot(Simulateur): void
    }
    class ElementaryFirefighterChief {
      + affectRobot(Simulateur): void
    }
    class AdvancedFireFighterChief {
      + affectRobot(Simulateur): void
    }
  }
  abstract FireFighterChief {
    + affectRobot(Simulateur): void
  }
}


package terrain {
  class Carte {
    + voisinExiste(Case, Direction): boolean
    + getVoisin(Case, Direction): Case
    + getCase(int, int): Case
    + getVoisins(Case): ArrayList<Case>
    + getTailleCases(): int
    + getdir(Case, Case): Direction
    + getNbColonnes(): int
    + getCases(): Iterable<Case>
    + setCase(Case): void
    + getNbLignes(): int
  }
  class Case {
    + getType(): NatureTerrain
    + setIncendie(Incendie): void
    + getCarte(): Carte
    + getLigne(): int
    + getIncendie(): Incendie
    + getColonne(): int
  }
  enum NatureTerrain {
    + TERRAIN_LIBRE:
    + FORET:
    + ROCHE:
    + HABITAT:
    + EAU:
    + valueOf(String): NatureTerrain
    + values(): NatureTerrain[]
  }
  enum Direction {
    + EST:
    + SUD:
    + OUEST:
    + NORD:
    + values(): Direction[]
    + valueOf(String): Direction
  }
}


package robot {
  abstract Robot {
    + getReservoir(): int
    + isAccessible(Case): boolean
    + followPath(Simulateur, Path): void
    + setPosition(Case): void
    + deverserEau(): int
    + startMove(Simulateur, Direction): void
    + isWaiting(Simulateur): boolean
    + startMove(Simulateur, Direction, Case): void
    + getTimeOn(Case): int
    + remplirReservoir(): void
    + startIntervention(Simulateur, boolean): void
    + remplir(Simulateur): void
    + getPosition(): Case
    + intervenir(Simulateur, long, boolean): void
    + getSpeedOn(Case): int
    + remplir(Simulateur, long): void
    + setSpeed(int): void
  }
  class RobotAChenille {
    + setPosition(Case): void
    + isAccessible(Case): boolean
    + findWater(Case): boolean
    + getSpeedOn(Case): int
    + setSpeed(int): void
  }
  class RobotAPattes {
    + findWater(Case): boolean
    + getSpeedOn(Case): int
    + setPosition(Case): void
    + isAccessible(Case): boolean
    + remplirReservoir(): void
    + getReservoir(): int
    + deverserEau(): int
  }
  class RobotARoues {
    + findWater(Case): boolean
    + isAccessible(Case): boolean
    + setPosition(Case): void
  }
  class Drone {
    + findWater(Case): boolean
    + setSpeed(int): void
    + isAccessible(Case): boolean
  }
}


package pathfinding {
  class Path {
    + getCarte(): Carte
    + addStep(Case): void
    + getStart(): Case
    + getDuration(): int
    + getPath(): LinkedList<Direction>
  }
  abstract SelfDriving {
    + getSpeedOn(Case): int
    + findWater(Case): boolean
    + getTimeOn(Case): int
    + Dijkstra(Case, CaseCompareCond): Path
    + isAccessible(Case): boolean
  }
}

' Positionning

scenarios --[hidden]> manage
Strategie --[hidden]> simu

manage --[hidden]> terrain
simu --[hidden]> robot

terrain --[hidden]> pathfinding
robot --[hidden]> pathfinding

NatureTerrain --down[hidden]> Direction
SelfDriving --right[hidden]> Path

' ' Heritage relationships

' ## FireFighterChiefs
AdvancedFireFighterChief   -[hidden]-^  FireFighterChief
ElementaryFirefighterChief -[hidden]-^  FireFighterChief
ImprovedFirefighterChief   -[hidden]-^  FireFighterChief
FireFighterChiefType   -[#0000A2,plain]-^  FireFighterChief

' ## Evenement
LancementStrategie        -[#0000A2,plain]-^  Evenement
RobotEven                 -[#0000A2,plain]-^  Evenement
' ### RobotEven
RemplissageEven           -up[hidden]-^  RobotEven
RobotTeleportEven         -[#0000A2,plain]-^  RobotEven
DebInterventionEven       -[hidden]-^  RobotEven
DebRemplissageEven        -[hidden]-^  RobotEven
DebRobotBougeEven         -[hidden]-^  RobotEven
InterventionEven          -up[hidden]-^  RobotEven
RobotBougeEven            -up[hidden]-^  RobotEven
DebEven         -[#0000A2,plain]-^  RobotEven
ActionEven            -up[#0000A2,plain]-^  RobotEven

' ' ## SelfDriving
Robot                    -[#0000A2,plain]-^  SelfDriving
' ### Robot
Drone              -[#0000A2,plain]-^  Robot
RobotAChenille     -[#0000A2,plain]-^  Robot
RobotAPattes       -[#0000A2,plain]-^  Robot
RobotARoues        -[#0000A2,plain]-^  Robot



' ## DonneesSimulation
DonneesSimulation    "1" *-[#595959,plain]-> Carte
DonneesSimulation    "*" *-[#595959,plain]-> Incendie
DonneesSimulation    *-[#595959,plain]-> "*" Robot

Carte             "*" *-[#595959,plain]-> "1" Case

Case              "1" o-[#595959,plain]-> "0..1" Incendie

LancementStrategie   "1" -[#595959,plain]-> "1" FireFighterChief    : call

Path          "*" o-[#595959,plain]-> "1" Carte
Path          "*" o-[#595959,plain]-> "1" SelfDriving

RobotEven      "*" -[#595959,plain]-> "1" Robot

Robot               "0..*" -[#595959,plain]--> "1" Case                           : "localisation"

RobotTeleportEven    "1" -[#595959,plain]-> "1" Case

Simulateur           "1" o-[#595959,plain]-> "1" DonneesSimulation
Simulateur           "1" *-[#595959,plain]-> "*" Evenement

scenarios                    --[#595959]->  Simulateur                         : "«create»"

LecteurDonnees             --[#595959]->  DonneesSimulation                  : "«create»"

Robot                   --[#595959]->  ActionEven                   : "«create»"


scenarios                     --[#595959]->  DebEven                : "«create»"

SelfDriving       --[#595959]->  Path                        : "«create»"

Strategie                       --[#595959]->  LecteurDonnees                  : "calls"
Strategie                       --[#595959]->  FireFighterChiefType                  : "create"
Strategie                       --[#595959]->  LancementStrategie                 : "create"
Strategie                       --[#595959]->  Simulateur                         : "create"

TestInvader                   --[#595959]->  Invader                                 : "«create»"




@enduml