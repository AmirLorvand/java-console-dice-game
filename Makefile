JAVAC = javac
JAVA = java
SRC_DIR = src
OUT_DIR = out
MAIN_CLASS = dice.game.DiceGame

all:
mkdir -p $(OUT_DIR)
$(JAVAC) -d $(OUT_DIR) $(SRC_DIR)/dice/game/DiceGame.java

run: all
$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS)

clean:
rm -rf $(OUT_DIR)
