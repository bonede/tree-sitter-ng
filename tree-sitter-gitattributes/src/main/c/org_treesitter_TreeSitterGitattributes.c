
#include <jni.h>
void *tree_sitter_gitattributes();
/*
 * Class:     org_treesitter_TreeSitterGitattributes
 * Method:    tree_sitter_gitattributes
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGitattributes_tree_1sitter_1gitattributes
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_gitattributes();
}
