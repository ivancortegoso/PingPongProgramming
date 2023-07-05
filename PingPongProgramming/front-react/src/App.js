import './App.css';
import React from 'react'
import {Route, Routes} from "react-router-dom";
import {SignUpWeb} from "./components/webs/SignUpWeb";
import {LogInWeb} from "./components/webs/LogInWeb";
import {BankAccountsWeb} from "./components/webs/BankAccountsWeb";
import {Menu} from "./components/Menu";
import {AccountMenu} from "./components/AccountMenu";
import {TransactionsWeb} from "./components/webs/TransactionsWeb";
import {TransactionDetailWeb} from "./components/webs/TransactionDetailWeb";

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

    render() {
        return (
            <div className="App">
                <AccountMenu isShown={this.state.showAccountMenu ? true : false}/>
                <div className="Web">
                    <header className="App-header">
                        <button className="AccountMenuButton" onClick={this.updateShowAccountMenu}>Acc menu</button>
                        <h1>Real World App</h1>
                        <Menu/>
                    </header>
                    <Routes>
                        <Route path="/" element={<div>In development</div>}/>
                        <Route path="/signup" element={<SignUpWeb/>}/>
                        <Route path="/login" element={<LogInWeb/>}/>
                        <Route path="/bankaccounts" element={<BankAccountsWeb/>}/>
                        <Route path="/transactions" element={<TransactionsWeb/>}/>
                        <Route path="/transaction" element={<TransactionDetailWeb/>}/>
                    </Routes>

                </div>

            </div>
        );

    }
}

