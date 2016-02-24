# CasualQuestJ
Basic RPG combat demo using assets from [Casual Quest BYOND](http://www.byond.com/games/iainperegrine/casualquest).

![](https://github.com/Lulu1494/CasualQuestJ/raw/master/2016-02-24_00-29-03.gif)

### Controls

* Move: Arrow keys
* Attack: Space
* Skills: 1-Z, 2-X, 3-C
* Reset: R
* Pause: P

### Items

Walk over them to pick them up.

They disappear after 10 seconds. 

* ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/items/cherry.png) Cherry: Heals you for 1 health. Can't be picked up if your health is full. 
* ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/items/gold1.png) Gold Coin: Adds 1 to your Gold count. Pretty much useless, though.
* ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/items/bottle.png) Bottle: Fills your aura completely. 
* ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/items/plum.png) Plum: Fills your health completely. 
* ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/items/shield.png) Shield: Grants invincibility for 3 seconds. 

### Classes 
Press the number on your keyboard to change to it.

1. ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/adventurer/east1.png) Adventurer 
  * Health: 2
  * Primary: ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/weapons/sword3.png) Wooden sword (1 damage)
2. ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/knight/east1.png) Knight 
  * Health: 8
  * Primary: ![](https://github.com/Lulu1494/CasualQuestJ/blob/master/CasualQuest/rsc/weapons/metalSword3.png) Steel sword (2 damage)
3. ![](https://github.com/Lulu1494/CasualQuestJ/blob/master/CasualQuest/rsc/mage/east1.png) Mage 
  * Health: 4
  * Aura: 4 (regen: 0.75 aura per second)
  * Primary: ![](https://github.com/Lulu1494/CasualQuestJ/blob/master/CasualQuest/rsc/weapons/fireball1.png) Fireball (1 damage, 1 aura, ranged)
  * Skill 1: ![](https://github.com/Lulu1494/CasualQuestJ/blob/master/CasualQuest/rsc/weapons/bigFire1.png) Big Fireball (2 damage, 2 aura)

### Enemies

All enemies deal damage on contact with the player.

Enemies spawn from the edges of the map. Keep your distance from the edges. 

* __Bug__

  Moves slowly in cardinal directions. 
  * ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/enemies/basic%20bug/east1.png) 
    * Health: 1
    * Damage: 1
  * ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/enemies/medium%20bug/east1.png) 
    * Health: 2
    * Damage: 1
  * ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/enemies/strong%20bug/east1.png) 
    * Health: 4
    * Damage: 2
* __Bird__ 

 Moves in straight diagonal lines pretty quickly and bounces on the sides.
  * ![](https://github.com/Lulu1494/CasualQuestJ/raw/master/CasualQuest/rsc/enemies/bird/east1.png)
    * Health: 1
    * Damage: 1
