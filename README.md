# DATX02-20-11
This is the product created when writing our bachelor thesis at Chalmers University and University of Gothenburg.
It is an application called Grammarlex, and it is meant for teaching grammar through different exercises. Currently there are three implemented exercises, Translation, Synonym and Fill the gap.
Grammarlex utilises [Grammatical Framework](http://www.grammaticalframework.org/) (GF) in order for it to be multilingual, so the exercises in the application should be able to work with all available languages. However our aim was to teach Swedish to non-native speakers.
It also utilises [GF WordNet](https://github.com/GrammaticalFramework/gf-wordnet) which is a WordNet port to GF.

The pattern on the lexicon toolbar is designed by GarryKillian at freepik.com, thus if you intend to release the application on the market, an attribution to freepik is required. Do this by adding the attribution line "Designed by Freepik" in a credit page in the application, and in the description of the application in the market/website where the application is released.

## Structure of the application
We will not go in-depth here, so if you have any questions feel free to contact the contributors of this project.

We used [Android Jetpack](https://developer.android.com/jetpack/) for constructing the app, and there are many great tutorials for it if you want to learn more. We recommend the "Room with a View" tutorial.

The database in the application is Room, and there is currently a database dump that is used when starting the application. This can be found in the assets folder.
There is also a folder called ```parsing``` which contains some python scripts that were used when parsing GF WordNet in order to extract information from it that was used in the database, for example the explanation and synonym set.

The exercises are also contained in the ```parsing``` folder. The Fill the gap exercises are inside of the ```Exercises.csv``` file, the translation exercises in ```TranslateExercises.csv``` and the synonym exercises in ```SynonymExercises.csv```.

If you want to add more exercises you need to make sure to build the database again, if you load it from the pre-existing assets any additions will not be found in the app.

There is also a vital file that is gitignored called Android.mk, located in ```jni```. In order to run this project, GF-core needs to be in the same directory as this is cloned to.

Our thesis is yet to be published, but when it is, more information about the project can be found in it. If we have not linked it here by then, feel free to contact us.

### Android.mk example

```
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

jni_c_files := jpgf.c jsg.c jni_utils.c
sg_c_files := sg.c sqlite3Btree.c
pgf_c_files := data.c expr.c graphviz.c linearizer.c literals.c parser.c parseval.c pgf.c printer.c reader.c \
reasoner.c evaluator.c jit.c typechecker.c lookup.c aligner.c writer.c scanner.c
gu_c_files := assert.c  choice.c  exn.c   fun.c   in.c      map.c  out.c     utf8.c \
bits.c    defs.c    enum.c  file.c  hash.c  mem.c  prime.c  seq.c   string.c  ucs.c   variant.c

LOCAL_MODULE    := jpgf
LOCAL_SRC_FILES := $(addprefix ../../../GF/gf-core/src/runtime/java/, $(jni_c_files)) \
                   $(addprefix ../../../GF/gf-core/src/runtime/c/sg/, $(sg_c_files)) \
                   $(addprefix ../../../GF/gf-core/src/runtime/c/pgf/, $(pgf_c_files)) \
                   $(addprefix ../../../GF/gf-core/src/runtime/c/gu/, $(gu_c_files))
LOCAL_C_INCLUDES := PATH_TO_WHERE_THIS_REPO_IS_CLONED/GF/gf-core/src/runtime/c

include $(BUILD_SHARED_LIBRARY)

$(realpath ../obj/local/armeabi/objs/jpgf/__/__/__/runtime/c/pgf/jit.o): lightning
$(realpath ../obj/local/armeabi/objs-debug/jpgf/__/__/__/runtime/c/pgf/jit.o): lightning

lightning:
	ln -s -f arm/asm.h ../../../runtime/c/pgf/lightning/asm.h
	ln -s -f arm/core.h ../../../runtime/c/pgf/lightning/core.h
	ln -s -f arm/fp.h ../../../runtime/c/pgf/lightning/fp.h
	ln -s -f arm/funcs.h ../../../runtime/c/pgf/lightning/funcs.h
```
