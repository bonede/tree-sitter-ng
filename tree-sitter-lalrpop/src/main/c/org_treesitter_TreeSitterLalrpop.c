
#include <jni.h>
void *tree_sitter_lalrpop();
/*
 * Class:     org_treesitter_TreeSitterLalrpop
 * Method:    tree_sitter_lalrpop
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterLalrpop_tree_1sitter_1lalrpop
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_lalrpop();
}
