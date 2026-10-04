"use client";

     import React, { useEffect, useState } from 'react';
     import { useParams } from 'next/navigation';
     import ChatInterface from './ChatInterface';

     export default function PublicCampaignChatPage() {
       const params = useParams();
       const campaignId = params.campaignId as string;

         const [campaign, setCampaign] = useState<any>(null);






        const [session, setSession] = useState<any>(null);
        const [loading, setLoading] = useState(true);
        const [error, setError] = useState<string | null>(null);

        useEffect(() => {
          if (!campaignId) return;

           async function initCampaignChat() {
             try {
               const apiUrl = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

                // 1. Fetch public campaign details
                const campRes = await fetch(`${apiUrl}/api/v1/public/campaigns/${campaignId}`);
                const campData = await campRes.json();
                if (!campRes.ok) throw new Error(campData.message || 'Campaign not found');
                setCampaign(campData.data);

                // 2. Initialize anonymous chat session
                const chatRes = await fetch(`${apiUrl}/api/v1/public/campaigns/${campaignId}/chat/init`, {
                  method: 'POST'
                });
                const chatData = await chatRes.json();
                if (!chatRes.ok) throw new Error(chatData.message || 'Failed to initialize chat');

                const sessionPayload = chatData.data;
                setSession(sessionPayload);

                // 3. Store session in sessionStorage for /book flow compatibility
                sessionStorage.setItem('leadflow_session', JSON.stringify({
                  token: sessionPayload.token,
                  businessId: sessionPayload.businessId,
                  campaignId: sessionPayload.campaignId,
                  conversationId: sessionPayload.conversationId,
                  sessionId: sessionPayload.sessionId
                }));

               } catch (err: any) {
                 setError(err.message);
               } finally {
                 setLoading(false);
               }
           }

          initCampaignChat();
        }, [campaignId]);

        if (loading) {
          return (
             <div className="flex items-center justify-center min-h-screen bg-gray-50">
               <p className="text-gray-500 font-medium animate-pulse">Loading chat session...</p>
             </div>
          );
        }







         if (error) {
           return (
              <div className="flex items-center justify-center min-h-screen bg-gray-50 p-6">
                <div className="bg-white p-6 rounded-xl shadow-sm border max-w-md w-full text-center">
                  <h2 className="text-xl font-bold text-red-600 mb-2">Unable to Load Chat</h2>
                  <p className="text-gray-600">{error}</p>
                </div>
              </div>
           );
         }

         return (
            <main className="min-h-screen bg-gray-50 flex flex-col">
              <header className="bg-white border-b py-4 px-6 shadow-sm">
                <h1 className="text-lg font-bold text-gray-800">{campaign?.businessName}</h1>
                <p className="text-xs text-gray-500">Campaign: {campaign?.name}</p>
              </header>
              <div className="flex-1 flex flex-col max-w-3xl w-full mx-auto p-4 sm:p-6">
                <ChatInterface session={session} />
              </div>
            </main>
         );
     }
