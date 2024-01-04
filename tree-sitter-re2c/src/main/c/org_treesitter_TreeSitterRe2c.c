
#include <jni.h>
void *tree_sitter_re2c();
/*
 * Class:     org_treesitter_TreeSitterRe2c
 * Method:    tree_sitter_re2c
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRe2c_tree_1sitter_1re2c
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_re2c();
}
