import React from 'react'
import {BankAccountItem} from "../BankAccountItem";
import {Link} from "react-router-dom";
import {Auth} from "../../Auth";


export class BankAccountsWeb extends React.Component {
    constructor() {
        super();
        this.state = {
            bankAccountsList: []
        }
        this.fetchList = this.fetchList.bind(this);
    }

    async fetchList() {
        const response = await fetch("http://localhost:8080/api/user/bankaccounts", {
            headers: {
                'Authorization': Auth.GetAuth()
            }
        });
        try {
            const bankList = await response.json();
            this.setState({bankAccountsList: bankList});

        } catch(e) {

        }
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
                        <Link to={"/bankaccount/create"}>
                            <button className={"StandardButton"}>CREATE</button>
                        </Link>
                    </div>

                    {this.state.bankAccountsList.length > 0 ? <div className={"HSeparator"}></div> : ""}
                    {this.state.bankAccountsList.map((item) => <BankAccountItem item={item}/>)}
                </div>
            </div>
        )
    }
}