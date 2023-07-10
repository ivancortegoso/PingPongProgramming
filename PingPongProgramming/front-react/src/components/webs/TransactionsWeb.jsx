import React from 'react'
import '../styles/TransactionsStyle.css'
import {TransactionItem} from "../TransactionItem";
import {Auth} from "../../Auth";
import {useParams} from "react-router-dom";
import {useState, useEffect} from 'react'


export const TransactionsWeb = () => {
    const [transactionList, setTransactionList] = useState([]);
    const {filter} = useParams();

    const fetchTransactions = async () => {
        const response = await fetch("http://localhost:8080/api/transaction/" + filter, {
            headers: {
                'Authorization' : Auth.GetAuth()
            }
        });
        const transList = await response.json();
        setTransactionList(transList);
    }

    useEffect(() => {
        fetchTransactions();
    }, [filter]);

    return (
        <div className={"TransactionsWeb"}>
            <div className={"TransactionsWeb-List ShadowBox"}>
                <label>Transactions</label>
                {transactionList.length > 0 ? <div className={"HSeparator"}></div> : ""}
                { transactionList.length > 0 &&
                    transactionList.map((item, index) => {
                        return(
                            <>
                                {index == 0 ? "" : <div className={"HSeparator"}></div>}
                                <TransactionItem item={item}/>
                            </>
                        )
                    })
                }

            </div>
        </div>
    )
    
}

/*export class TransactionsWeb extends React.Component {

    constructor() {
        super();
        this.state = {
            transactionList: [],
        }
        this.fetchTransactions = this.fetchTransactions.bind(this)
    }

    async fetchTransactions() {
        const response = await fetch("http://localhost:8080/api/transaction/" + filter, {
            headers: {
                'Authorization' : Auth.GetAuth()
            }
        });
        const transList = await response.json();
        this.setState({transactionList: transList});
    }

    componentDidMount() {
        this.fetchTransactions()
    }

    render() {
        return (
            <div className={"TransactionsWeb"}>
                <div className={"TransactionsWeb-List ShadowBox"}>
                    Transactions
                    { this.state.transactionList.length > 0 &&
                        this.state.transactionList.map((item, index) => {
                            return(
                                <>
                                    {index == 0 ? "" : <div className={"HSeparator"}></div>}
                                    <TransactionItem item={item}/>
                                </>
                            )
                        })
                    }

                </div>
            </div>
        )
    }
}*/