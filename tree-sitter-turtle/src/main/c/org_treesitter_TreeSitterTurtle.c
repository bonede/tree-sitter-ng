
#include <jni.h>
void *tree_sitter_turtle();
/*
 * Class:     org_treesitter_TreeSitterTurtle
 * Method:    tree_sitter_turtle
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterTurtle_tree_1sitter_1turtle
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_turtle();
}
