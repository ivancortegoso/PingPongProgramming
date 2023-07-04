import logo from './logo.svg';
import './App.css';
import {Route, Routes} from "react-router-dom";
import {SignUpWeb} from "./components/webs/SignUpWeb";
import {LogInWeb} from "./components/webs/LogInWeb";
import {BankAccountsWeb} from "./components/webs/BankAccountsWeb";
import {Menu} from "./components/Menu";

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <h1>Real World App</h1>
                <Menu/>
            </header>
            <Routes>
                <Route path="/" element={<div>In development</div>}/>
                <Route path="/signup" element={<SignUpWeb/>}/>
                <Route path="/login" element={<LogInWeb/>}/>
                <Route path="/bankaccounts" element={<BankAccountsWeb/>}/>
            </Routes>
        </div>
    );
}

export default App;
