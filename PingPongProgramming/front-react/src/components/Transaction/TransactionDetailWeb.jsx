import React, { useEffect, useState } from 'react'
import '../styles/TransactionDetailStyle.css'
import { useParams } from 'react-router-dom'
import { Auth } from '../../Auth'


export const TransactionDetailWeb = () => {
    const [info, setInfo] = useState([]);
    const {id} = useParams();

    const commentaries = ["aaa", "bbb", "ccc", "dddd"];


    const fetchInfo = async() => {
        const response = await fetch("http://localhost:8080/api/transaction/" + id, {
            method: 'get',
            headers:{
                'Authorization' : Auth.GetAuth(),
            }
        });
        if(response.ok) {
            const resinfo = await response.json();
            setInfo(resinfo);
        }
    }

    useEffect(() => {fetchInfo()});

    const addComment = (e) => {
        e.preventDefault();
        e.target.reset();
    }


    return (
        <div className={"TransactionDetailWeb ShadowBox"}>
            <div><h3>Transaction Detail</h3></div>
            <div className='Space-Between'>
                <div style={{display: 'inline-block'}}>
                    <div><label>{"<Name1> paid <Name2>"}</label></div>
                    <div><label>{"Payment: <ID1> to <ID2>"}</label></div>
                </div>
                <div style={{display: 'inline-block'}}>
                    <label>Balance</label>
                </div>

            </div>
            <div>0 LIKES</div>
            <div>
                <ul>
                    {commentaries.map((text) => {
                        return (
                            <li className="Comment-Box">Author:  text </li>
                        );
                    })}
                </ul>
            </div>
            <form onSubmit={addComment}>
                <input className="WriteCommentBox" type="text" placeholder="Write a comment..."/>
            </form>
        </div>
    )
}