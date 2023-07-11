import './App.css';
import React from 'react'
import {Navigate, Route, Routes} from "react-router-dom";
import {SignUpWeb} from "./components/webs/SignUpWeb";
import {LogInWeb} from "./components/webs/LogInWeb";
import {BankAccountsWeb} from "./components/BankAccount/BankAccountsWeb";
import {Menu} from "./components/Menu";
import {AccountMenu} from "./components/AccountMenu";
import {TransactionsWeb} from "./components/Transaction/TransactionsWeb";
import {TransactionDetailWeb} from "./components/Transaction/TransactionDetailWeb";
import {Auth} from "./Auth";
import {TransactionCreateWeb} from "./components/Transaction/TransactionCreateWeb";
import {BankAccountCreateWeb} from "./components/BankAccount/BankAccountCreateWeb";
import {UserSettingsWeb} from "./components/webs/UserSettingsWeb";
import {TransparentBlackBackground} from "./components/TransparentBlackBackground"

export class App extends React.Component {
    constructor() {
        super();
        this.state = {
            showAccountMenu: false
        };
        this.updateShowAccountMenu = this.updateShowAccountMenu.bind(this);
    }

    updateShowAccountMenu() {
        const lastShow = this.state.showAccountMenu;
        this.setState( {
            showAccountMenu: !lastShow,
        });
    }

    NoLoggedRoutes() {
        return (
            <React.Fragment>
                <Route path="login" element={<LogInWeb/>}/>
                <Route path="signup" element={<SignUpWeb/>}/>
                <Route path="*" element={<Navigate to={"login"} replace/>}/>
            </React.Fragment>
        )
    }

    LoggedRoutes() {
        return (
            <React.Fragment>
                <Route path="bankaccounts" element={<BankAccountsWeb/>}/>
                <Route path="bankaccount/create" element={<BankAccountsWeb/>}/>
                <Route path="transactions">
                    <Route path=":filter/*" element={<TransactionsWeb/>}>
                    </Route>
                </Route>
                <Route path="transaction/*" element={<TransactionDetailWeb/>}/>
                {/*<Route path="transaction/create" element={<TransactionCreateWeb/>}/>
                <Route path="bankaccount/create" element={<BankAccountCreateWeb/>}/>*/}
                <Route path="user/settings" element={<UserSettingsWeb/>}/>
                <Route path="*" element={<Navigate to={"bankaccounts"} replace/>}/>
            </React.Fragment>
        )
    }

    render() {
        return (
            <div className="App">
                <AccountMenu isShown={this.state.showAccountMenu ? true : false}/>
                <div className="Web">
                    <header className="App-header">
                        <button className="AccountMenuButton" onClick={this.updateShowAccountMenu}>Acc menu</button>
                        <h1>Real World App</h1>
                        {Auth.IsLogged() ? <Menu/> : ""}
                    </header>
                    <Routes>
                        {Auth.IsLogged() ? this.LoggedRoutes() : this.NoLoggedRoutes()}
                    </Routes>
                    <Routes>
                        <Route path="bankaccount/create" element={<><TransparentBlackBackground/><BankAccountCreateWeb/></>}/>
                        <Route path="*" element={""}/>
                    </Routes>
                </div>
            </div>
        );

    }
}

