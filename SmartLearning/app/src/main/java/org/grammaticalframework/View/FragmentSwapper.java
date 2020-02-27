package org.grammaticalframework.View;

import org.grammaticalframework.R;
import org.grammaticalframework.View.Fragments.BaseFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentSwapper {
    FragmentManager fragmentManager;
    public FragmentSwapper(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }
   public void initFragments(int id, BaseFragment fragment){
       FragmentTransaction transaction = fragmentManager.beginTransaction();
       transaction.replace(id,fragment).addToBackStack(null);
       transaction.commit();
   }

   public void changeToFragment(BaseFragment fragment){
       FragmentTransaction transaction = fragmentManager.beginTransaction();
       transaction.replace(R.id.container,fragment).addToBackStack(null);
       transaction.commit();
   }

   public void onBackPressed(){
       if (fragmentManager.getBackStackEntryCount() > 0) {
           fragmentManager.popBackStack();
       } else {
           //super.onBackPressed();
       }
   }
}
