package com.techparew.x_files.model;

import java.sql.Date;

public class Account {
        private int idAccount;
        private String password;
        private Date accountCreated;
        private Date lastSeen;
        private boolean onlineStatus;
        private AccountType accountType;
        private User user;

        public Account(User user, AccountType accountType){
            this.user = user;
            this.accountType = accountType;
        }
}
