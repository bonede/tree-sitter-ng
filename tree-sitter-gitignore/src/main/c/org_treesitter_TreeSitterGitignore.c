
#include <jni.h>
void *tree_sitter_gitignore();
/*
 * Class:     org_treesitter_TreeSitterGitignore
 * Method:    tree_sitter_gitignore
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGitignore_tree_1sitter_1gitignore
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_gitignore();
}
