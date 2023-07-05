import React from 'react'
import {BankAccountItem} from "../BankAccountItem";


export class BankAccountsWeb extends React.Component {
    constructor() {
        super();
        this.state = {
            bankAccountsList: []
        }
        this.fetchList = this.fetchList.bind(this);
    }

    async fetchList() {
        const response = await fetch("/api/bankaccounts", {
            headers: {
                'Authorization': localStorage.getItem("token")
            }
        });
        const bankList = await response.json();
        if(!bankList.empty())
            this.setState({bankAccountsList: bankList});
    }

    async fetchCreate() {

    }

    componentDidMount() {
        this.fetchList()
    }

    render() {
        return (
            <div className="BankAccountsWeb">
                <div className={"BankAccountsWeb-List ShadowBox"}>
                    <div className={"BankAccountsWeb-Header Space-Between"}>
                        <label>Bank Accounts</label>
                        <button className={"StandardButton"}>CREATE</button>
                    </div>

                    {this.state.bankAccountsList.length > 0 ? <div className={"HSeparator"}></div> : ""}
                    {
                        this.state.bankAccountsList.map((item) => <BankAccountItem item={item}/>)
                    }
                </div>
            </div>
        )
    }
}