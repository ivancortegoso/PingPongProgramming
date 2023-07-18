import React from 'react'
import {useState, useEffect} from 'react'
import {BankAccountItem} from "./BankAccountItem";
import {Auth} from "../../Auth";
import Popup from 'reactjs-popup';
import { BankAccountCreateWeb } from './BankAccountCreateWeb';
import '../styles/TableStyle.css'


export const BankAccountsWeb = () => {
    const [bankAccountsList, setBankAccountsList] = useState([]);

    const fetchList = async() => {
        const response = await fetch("http://localhost:8080/api/bankaccount", {
            headers:{
                'Authorization' : Auth.GetAuth()
            },
        });
        try {
            const bankList = await response.json();
            setBankAccountsList(bankList);
            console.log("something");

        } catch(e) {

        }

    }

    useEffect(() => {fetchList()}, []);

    return (
        <div className="BankAccountsWeb">
            <div className={"BankAccountsWeb-List ShadowBox"}>
                <div className={"BankAccountsWeb-Header Space-Between"}>
                    <h2>Bank Accounts</h2>
                    <Popup trigger={<button className={"Create-Button"}>Create</button>} modal nested>
                        {close => (<BankAccountCreateWeb onClose={close} onAccept={fetchList}/>)}
                    </Popup>
                </div>
                <table className="Default-Table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Balance</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {bankAccountsList.map((item) => <BankAccountItem key={item["id"]} item={item} onDeleted={fetchList}/>)}
                    </tbody>
                </table>
                
            </div>
        </div>
    )
}
