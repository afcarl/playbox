# Probably one of the worst makefile I ever wrote
# Note that playbox.jar contains absolutely everything is needs.
# Not the best form, but tightly controls with version of its
# dependency it uses.

PLAY_SRCS := $(wildcard playground/*.java)
PLAY_SRCS += $(wildcard playground/*/*.java)
PLAY_SRCS += $(wildcard playground/*/*/*.java)

PBOX_SRCS := $(wildcard playbox/*.java)
PBOX_SRCS += $(wildcard playbox/*/*.java)
PBOX_SRCS += $(wildcard playbox/*/*/*.java)

all: playground playbox

clean:
	rm -Rf target/*;

playground_class: $(PLAY_SRCS)
	javac -cp external/jbox2d.jar src/playground/*.java src/playground/*/*.java -d target/;

playground: playground_class
	jar -cf target/playground.jar target/playground

playbox_class: playground_class $(PBOX_SRCS)
	javac -cp external/jbox2d.jar:target/:external/core.jar src/playbox/*.java -d target/

playdoc:
	javadoc -quiet -classpath external/core.jar:external/jbox2d.jar src/playground/*.java src/playground/*/*.java -d docs/playground;

playboxjar: playground_class playbox_class
	cd target/ && \
	mkdir -p inflate && \
	rm -Rf inflate/* && \
	unzip -q -o ../external/jbox2d.jar    -d inflate && \
	unzip -q -o ../external/core.jar      -d inflate && \
	unzip -q -o ../external/slf4j-nop.jar -d inflate && \
	rm -Rf inflate/META-INF && \
	cp -Rf playground inflate/ && \
	cp -Rf playbox    inflate/ && \
	cd inflate && \
	jar -cf ../playbox.jar .; \
	cd .. && \
	rm -Rf inflate;

playbox: playboxjar playdoc
	cd target/ && \
	rm -Rf libraries/ && \
	mkdir libraries && \
	mkdir libraries/playbox && \
	mkdir libraries/playbox/library/ && \
	cp playbox.jar libraries/playbox/library/ && \
	mkdir libraries/playbox/examples && \
	cp -Rf ../examples/processing/* libraries/playbox/examples && \
	mkdir libraries/playbox/docs && \
	cp -Rf ../docs/* libraries/playbox/docs;
