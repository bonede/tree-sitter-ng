
#include <jni.h>
void *tree_sitter_pascal();
/*
 * Class:     org_treesitter_TreeSitterPascal
 * Method:    tree_sitter_pascal
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterPascal_tree_1sitter_1pascal
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_pascal();
}
