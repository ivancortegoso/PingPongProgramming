import React from 'react'
import './styles/BankAccountsStyle.css'

export class BankAccountItem extends React.Component {

    async fetchDelete() {
        const response = await fetch("/api/bankaccounts/" + this.props.item["id"], {
            method:"delete"
        });
    }

    render() {
        return (
            <div className={"BankAccountItem Space-Between"}>
                <label>{this.props.item["name"]}</label>
                <button className={"StandardButton"}>DELETE</button>
            </div>
        )
    }
}