"use client";

     import React, { useState, useRef, useEffect } from 'react';
     import { useRouter } from 'next/navigation';
     import { Send, Bot, User, Calendar } from 'lucide-react';

     interface Message {
       id?: string;
       senderType: 'CUSTOMER' | 'AI';
       content: string;
     }

     export default function ChatInterface({ session }: { session: any }) {
       const router = useRouter();
       const [messages, setMessages] = useState<Message[]>([
         { senderType: 'AI', content: `Hello! Welcome to our chat. How can I assist you today?` }
       ]);
       const [input, setInput] = useState('');
       const [loading, setLoading] = useState(false);
       const messagesEndRef = useRef<HTMLDivElement>(null);

         const scrollToBottom = () => {
            messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
         };

         useEffect(() => {





          scrollToBottom();
        }, [messages, loading]);

        const handleSendMessage = async (e: React.FormEvent) => {
          e.preventDefault();
          if (!input.trim() || loading) return;

           const userMessageContent = input.trim();
           setInput('');
           setMessages(prev => [...prev, { senderType: 'CUSTOMER', content: userMessageContent }]);
           setLoading(true);

           try {
             const apiUrl = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';
             const res = await fetch(`${apiUrl}/api/v1/customer/chat/${session.conversationId}`, {
               method: 'POST',
               headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${session.token}`
               },
               body: JSON.stringify({ content: userMessageContent })
             });

             const data = await res.json();
             if (!res.ok) throw new Error(data.message || 'Failed to process message');

             const aiReply = data.data;
             setMessages(prev => [...prev, { senderType: 'AI', content: aiReply.content }]);
           } catch (err: any) {
             setMessages(prev => [...prev, { senderType: 'AI', content: "Sorry, I encountered an error processing your request." }]);
           } finally {
             setLoading(false);
           }
        };

        const renderMessageContent = (content: string) => {
          // Check if message contains appointment booking link markdown [here](/book)
          if (content.includes('/book')) {
            const parts = content.split('[here](/book)');
            return (
               <span>
                 {parts[0]}
                 <button
                   onClick={() => router.push('/book')}
                   className="inline-flex items-center space-x-1 text-blue-600 underline font-semibold mx-1 hover:text-blue-800"
                 >
                   <Calendar className="w-4 h-4 inline" />
                   <span>here</span>
                 </button>
                 {parts[1]}
               </span>
            );
          }






           return content;
         };

         return (
           <div className="flex-1 flex flex-col bg-white rounded-xl shadow-sm border overflow-hidden">
             {/* Messages Feed */}
             <div className="flex-1 overflow-y-auto p-4 space-y-4">
               {messages.map((msg, idx) => {
                  const isAi = msg.senderType === 'AI';
                  return (
                     <div key={idx} className={`flex items-start space-x-3 ${isAi ? '' : 'flex-row-reverse space-x-reverse'}`}>
                       <div className={`w-8 h-8 rounded-full flex items-center justify-center shrink-0 ${isAi ? 'bg-blue-100 text-blue-600' : 'bg-gray-100 text-gray-60
                         {isAi ? <Bot className="w-5 h-5" /> : <User className="w-5 h-5" />}
                       </div>
                       <div className={`max-w-[75%] p-3.5 rounded-2xl text-sm leading-relaxed ${isAi ? 'bg-gray-100 text-gray-800 rounded-tl-none' : 'bg-blue-600 text-
                         {renderMessageContent(msg.content)}
                       </div>
                     </div>
                  );
               })}
               {loading && (
                  <div className="flex items-start space-x-3">
                     <div className="w-8 h-8 rounded-full bg-blue-100 text-blue-600 flex items-center justify-center">
                       <Bot className="w-5 h-5" />
                     </div>
                     <div className="bg-gray-100 p-3.5 rounded-2xl text-sm text-gray-500 animate-pulse">
                       AI is typing...
                     </div>
                  </div>
               )}
               <div ref={messagesEndRef} />
             </div>

             {/* Input Form */}
             <form onSubmit={handleSendMessage} className="p-3 border-t bg-gray-50 flex items-center space-x-2">
               <input
                  type="text"
                  value={input}
                  onChange={(e) => setInput(e.target.value)}
                  placeholder="Type your message..."
                  className="flex-1 bg-white border border-gray-300 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
               />
               <button
                  type="submit"
                  disabled={loading || !input.trim()}
                  className="bg-blue-600 text-white p-2.5 rounded-xl hover:bg-blue-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
               >
                  <Send className="w-5 h-5" />
               </button>
             </form>
           </div>
         );
     }
