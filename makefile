ENTER_POINT = mPic.Main
RES_DIR = no
SOURCE_FILES = \
mPic/Main.java \
mPic/ImagetoArrayUtils.java

JAVAC = javac

vpath %.class bin
vpath %.java src

Default:
	@echo "[HANDLE] making dirs"	
	@mkdir -pv src bin
	@echo "[HINT] compiling..."
	@$(JAVAC) -cp bin -classpath bin -d bin src/mPic/ImagetoArrayUtils.java
	@echo "[COMPILE] compile tools finished,start compiling Enter Point"
	@$(JAVAC) -cp bin -classpath bin -d bin src/mPic/Main.java
	@echo "[COMPILE] compile Enter Point finished"
	@echo "[RUN] Please input \"java -cp bin mPic.Main [srcPic] [wid(def or int)] [hei(def or int)] [outPath]\" to run"
