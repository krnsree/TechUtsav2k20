package com.example.techutsav.models;

import androidx.fragment.app.Fragment;

public interface NavigationHost {


     void navigateTo(Fragment fragment, boolean backStack);


}