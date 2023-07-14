import './App.css';
import React, { useState } from 'react'
import {Navigate, Route, Routes} from "react-router-dom";
import {SignUpWeb} from "./components/webs/SignUpWeb";
import {LogInWeb} from "./components/webs/LogInWeb";
import {BankAccountsWeb} from "./components/BankAccount/BankAccountsWeb";
import {Menu} from "./components/Menu";
import {AccountMenu} from "./components/AccountMenu";
import {TransactionsWeb} from "./components/Transaction/TransactionsWeb";
import {TransactionDetailWeb} from "./components/Transaction/TransactionDetailWeb";
import {Auth} from "./Auth";
import {UserSettingsWeb} from "./components/webs/UserSettingsWeb";
import { MenuSvg } from './components/svg-imgs/MenuSvg';


export const App = () => {
    const [showAccountMenu, setShowAccountMenu] = useState();
    const [isLogged, setIsLogged] = useState(false);

    const updateShowAccountMenu = () => {
        const lastShow = showAccountMenu;
        setShowAccountMenu(!lastShow);
    }

    const NoLoggedRoutes = () => {
        return (
            <React.Fragment>
                <Route path="login" element={<LogInWeb onLogin={setIsLogged}/>}/>
                <Route path="signup" element={<SignUpWeb/>}/>
                <Route path="*" element={<Navigate to={"login"} replace/>}/>
            </React.Fragment>
        )
    }

    const LoggedRoutes = () => {
        return (
            <React.Fragment>
                <Route path="bankaccounts" element={<BankAccountsWeb/>}/>
                <Route path="bankaccount/create" element={<BankAccountsWeb/>}/>
                <Route path="transactions" element={<TransactionsWeb/>}/>
                <Route path="transaction/:id" element={<TransactionDetailWeb/>}/>
                <Route path="user/settings" element={<UserSettingsWeb/>}/>
                <Route path="*" element={<Navigate to={"bankaccounts"} replace/>}/>
            </React.Fragment>
        )
    }

    return (
        <div className="App">
            <AccountMenu  onLogout={setIsLogged} display={showAccountMenu ? true : false}/>
            <div className="Web">
                <header className="App-header">
                    <button id="account-menu-id" className="AccountMenuButton" onClick={updateShowAccountMenu}><MenuSvg height="3em"/></button>
                    <h1>Real World App</h1>
                    {Auth.IsLogged() ? <Menu/> : ""}
                </header>
                <Routes>
                    {Auth.IsLogged() ? LoggedRoutes() : NoLoggedRoutes()}
                </Routes>
            </div>
        </div>
    );
}

    

