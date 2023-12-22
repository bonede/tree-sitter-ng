
#include <jni.h>
void *tree_sitter_beancount();
/*
 * Class:     org_treesitter_TreeSitterBeancount
 * Method:    tree_sitter_beancount
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterBeancount_tree_1sitter_1beancount
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_beancount();
}
