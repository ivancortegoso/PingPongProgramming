import React from 'react'
import {useState, useEffect} from 'react'
import {BankAccountItem} from "./BankAccountItem";
import {Auth} from "../../Auth";
import Popup from 'reactjs-popup';
import { BankAccountCreateWeb } from './BankAccountCreateWeb';


export const BankAccountsWeb = () => {
    const [bankAccountsList, setBankAccountsList] = useState([]);

    const fetchList = async() => {
        const response = await fetch("http://localhost:8080/api/user/bankaccounts", {
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
                    <b>Bank Accounts</b>
                    <Popup trigger={<button className={"StandardButton"}>CREATE</button>} onClose={fetchList} modal nested>
                        {close => (<BankAccountCreateWeb onClose={close}/>)}
                    </Popup>
                </div>

                {bankAccountsList.length > 0 ? <div className={"HSeparator"}></div> : ""}
                {bankAccountsList.map((item) => <BankAccountItem key={item["id"]} item={item}/>)}
            </div>
        </div>
    )
}
