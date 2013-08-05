# Probably one of the worst makefile I ever wrote
# Note that procbox.jar contains absolutely everything is needs.
# Not the best form, but tightly controls with version of its
# dependency it uses.

PLAY_SRCS := $(wildcard playground/*.java)
PLAY_SRCS += $(wildcard playground/*/*.java)
PLAY_SRCS += $(wildcard playground/*/*/*.java)

PBOX_SRCS := $(wildcard procbox/*.java)
PBOX_SRCS += $(wildcard procbox/*/*.java)
PBOX_SRCS += $(wildcard procbox/*/*/*.java)

all: playground procbox

clean:
	rm -Rf target/*;

playground_class: $(PLAY_SRCS)
	javac -cp external/jbox2d.jar src/playground/*.java src/playground/*/*.java -d target/;

playground: playground_class
	jar -cf target/playground.jar target/playground

procbox_class: playground_class $(PBOX_SRCS)
	javac -cp external/jbox2d.jar:target/:external/core.jar src/procbox/*.java -d target/

playdoc:
	javadoc -quiet -classpath external/core.jar:external/jbox2d.jar src/playground/*.java src/playground/*/*.java -d docs/playground;

procboxjar: playground_class procbox_class
	cd target/ && \
	mkdir -p inflate && \
	rm -Rf inflate/* && \
	unzip -q -o ../external/jbox2d.jar    -d inflate && \
	unzip -q -o ../external/core.jar      -d inflate && \
	unzip -q -o ../external/slf4j-nop.jar -d inflate && \
	rm -Rf inflate/META-INF && \
	cp -Rf playground inflate/ && \
	cp -Rf procbox    inflate/ && \
	cd inflate && \
	jar -cf ../procbox.jar .; \
	cd .. && \
	rm -Rf inflate;

procbox: procboxjar playdoc
	cd target/ && \
	rm -Rf libraries/ && \
	mkdir libraries && \
	mkdir libraries/procbox && \
	mkdir libraries/procbox/library/ && \
	cp procbox.jar libraries/procbox/library/ && \
	mkdir libraries/procbox/examples && \
	cp -Rf ../examples/processing/* libraries/procbox/examples && \
	mkdir libraries/procbox/docs && \
	cp -Rf ../docs/* libraries/procbox/docs;
