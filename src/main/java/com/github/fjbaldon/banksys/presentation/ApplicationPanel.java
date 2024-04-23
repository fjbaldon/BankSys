package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Model;

import javax.swing.*;

public interface ApplicationPanel {

    void show(JFrame frame, Model optional);
}
