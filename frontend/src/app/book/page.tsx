"use client";

     import React, { useState, useEffect } from 'react';
     import { useRouter } from 'next/navigation';

     export default function BookPage() {
       const router = useRouter();
       const [date, setDate] = useState<string>('');
       const [slots, setSlots] = useState<string[]>([]);
       const [selectedSlot, setSelectedSlot] = useState<string | null>(null);
       const [isConfirmed, setIsConfirmed] = useState(false);
       const [loading, setLoading] = useState(false);
       const [error, setError] = useState<string | null>(null);
       const [campaignId, setCampaignId] = useState<string | null>(null);

         useEffect(() => {
           const sessionData = sessionStorage.getItem('leadflow_session');
           if (!sessionData) {
             setError("No active session found. Please return to your conversation.");
             return;
           }
           try {
             const parsed = JSON.parse(sessionData);
             if (!parsed.token || !parsed.businessId) {
               setError("Invalid session data. Please return to your conversation.");
             } else {
               setCampaignId(parsed.campaignId || null);
             }
           } catch (e) {
             setError("Session error. Please return to your conversation.");
           }
         }, []);






        const fetchSlots = async (selectedDate: string) => {
           setDate(selectedDate);
           setSelectedSlot(null);
           setLoading(true);
           setError(null);
           try {
             const sessionData = JSON.parse(sessionStorage.getItem('leadflow_session') || '{}');
             const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'}/api/v1/customer/appointments/slots?date=${selectedDate}`, {
               headers: {
                 'Authorization': `Bearer ${sessionData.token}`
               }
             });
             const data = await res.json();
             if (!res.ok) throw new Error(data.message || 'Failed to fetch slots');
             setSlots(data.data);
           } catch (err: any) {
             setError(err.message);
           } finally {
             setLoading(false);
           }
        };

        const handleBooking = async () => {
           if (!selectedSlot) return;
           setLoading(true);
           setError(null);
           try {
             const sessionData = JSON.parse(sessionStorage.getItem('leadflow_session') || '{}');
             const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'}/api/v1/customer/appointments`, {
               method: 'POST',
               headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${sessionData.token}`
               },
               body: JSON.stringify({ startTime: selectedSlot })
             });
             const data = await res.json();
             if (!res.ok) throw new Error(data.message || 'Failed to book appointment');
             setIsConfirmed(true);
           } catch (err: any) {
             setError(err.message);
           } finally {
             setLoading(false);
           }
        };

        if (error && !date) {
          return <div className="p-8 text-center text-red-600">{error}</div>;
        }

        if (isConfirmed) {
          return (






              <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 p-6">
                <div className="bg-white p-8 rounded-xl shadow-sm border max-w-md w-full text-center">
                  <div className="w-16 h-16 bg-green-100 text-green-600 rounded-full flex items-center justify-center mx-auto mb-4 text-3xl">✓</div>
                  <h2 className="text-2xl font-bold text-gray-800 mb-2">Booking Confirmed!</h2>
                  <p className="text-gray-600 mb-6">Your appointment has been successfully scheduled for {new Date(selectedSlot!).toLocaleString()}.</p>
                  {campaignId && (
                     <button
                       onClick={() => router.push(`/c/${campaignId}`)}
                       className="w-full bg-blue-600 text-white font-medium py-2 rounded-lg hover:bg-blue-700 transition"
                     >
                       Return to Conversation
                     </button>
                  )}
                </div>
              </div>
            );
        }

        return (
          <div className="min-h-screen bg-gray-50 p-6 flex flex-col items-center">
            <div className="bg-white p-8 rounded-xl shadow-sm border max-w-2xl w-full">
              <h1 className="text-2xl font-bold text-gray-800 mb-6">Schedule an Appointment</h1>

                 {error && <div className="bg-red-50 text-red-600 p-4 rounded-lg mb-6">{error}</div>}

                 <div className="mb-6">
                   <label className="block text-sm font-medium text-gray-700 mb-2">Select Date</label>
                   <input
                      type="date"
                      min={new Date().toISOString().split('T')[0]}
                      onChange={(e) => fetchSlots(e.target.value)}
                      className="w-full p-3 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                   />
                 </div>

                 {loading && <p className="text-gray-500">Loading available times...</p>}

                 {!loading && date && slots.length === 0 && (
                    <p className="text-gray-500">No available slots for this date.</p>
                 )}

                 {!loading && slots.length > 0 && (
                   <div className="mb-8">
                     <label className="block text-sm font-medium text-gray-700 mb-2">Select Time</label>
                     <div className="grid grid-cols-2 sm:grid-cols-3 gap-3">
                       {slots.map(slot => {
                         const timeString = new Date(slot).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
                         return (
                           <button
                             key={slot}
                             onClick={() => setSelectedSlot(slot)}
                             className={`p-3 rounded-lg border font-medium text-sm transition ${
                               selectedSlot === slot






                                       ? 'bg-blue-600 text-white border-blue-600'
                                       : 'bg-white text-gray-700 border-gray-200 hover:border-blue-500'
                                 }`}
                             >
                               {timeString}
                             </button>
                           );
                        })}
                      </div>
                    </div>
                 )}

               <button
                  disabled={!selectedSlot || loading}
                  onClick={handleBooking}
                  className="w-full bg-blue-600 text-white font-medium py-3 rounded-lg hover:bg-blue-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
               >
                  Confirm Booking
               </button>
             </div>
           </div>
         );
     }

     BATCH 3 RESULT
     1. Files fully recovered:

              backend/src/main/java/com/leadflow/chat/entity/Lead.java

              backend/src/main/java/com/leadflow/chat/entity/Conversation.java

              backend/src/main/java/com/leadflow/chat/entity/Message.java

              backend/src/main/java/com/leadflow/chat/entity/SenderType.java

              backend/src/main/java/com/leadflow/chat/repository/LeadRepository.java

              backend/src/main/java/com/leadflow/chat/repository/ConversationRepository.java

              backend/src/main/java/com/leadflow/chat/repository/MessageRepository.java

              backend/src/main/java/com/leadflow/chat/service/ChatService.java

              backend/src/main/java/com/leadflow/chat/controller/CustomerChatController.java

              backend/src/main/java/com/leadflow/sales/controller/CustomerAppointmentController.java

              frontend/src/app/book/page.tsx

     2. Files not fully recoverable:

              backend/src/main/java/com/leadflow/knowledge/dto/KnowledgeDto.java

              backend/src/main/java/com/leadflow/knowledge/entity/BusinessKnowledge.java






           backend/src/main/java/com/leadflow/knowledge/entity/KnowledgeType.java

           backend/src/main/java/com/leadflow/knowledge/repository/KnowledgeRepository.java

           backend/src/main/java/com/leadflow/knowledge/service/KnowledgeService.java

           backend/src/main/java/com/leadflow/knowledge/controller/KnowledgeController.java

           backend/src/main/java/com/leadflow/chat/service/AiServiceClient.java

           backend/src/main/java/com/leadflow/chat/controller/PublicChatController.java

           frontend/src/app/c/[campaignId]/page.tsx

           frontend/src/app/c/[campaignId]/ChatInterface.tsx

     3. Compatibility Uncertainty: Because the AiServiceClient.java implementation and the frontend /c/[campaignId] React components were not fully recoverable from the chat history, their exact
     interaction patterns with the recovered files (like ChatService.java and BookPage.tsx) remain uncertain. The restored code correctly references them based on previous snippets, but actual compilation will
     fail until these unrecoverable components are manually re-implemented according to the Phase 9 architecture standards.



     User prompt: Continue repository recovery. Do NOT redesign, refactor, or create a new phase. We are reconstructing the original LeadFlow AI project from the conversation because no downloadable source artifact
     exists. Recover ONLY the following missing files: ### BATCH 4 — KNOWLEDGE + AI CLIENT + PUBLIC CHAT​1. backend/src/main/java/com/leadflow/knowledge/dto/KnowledgeDto.java 2.
     backend/src/main/java/com/leadflow/knowledge/entity/BusinessKnowledge.java 3. backend/src/main/java/com/leadflow/knowledge/entity/KnowledgeType.java 4.
     backend/src/main/java/com/leadflow/knowledge/repository/KnowledgeRepository.java 5. backend/src/main/java/com/leadflow/knowledge/repository/BusinessKnowledgeRepository.java 6.
     backend/src/main/java/com/leadflow/knowledge/service/KnowledgeService.java 7. backend/src/main/java/com/leadflow/knowledge/controller/KnowledgeController.java 8.
     backend/src/main/java/com/leadflow/chat/service/AiServiceClient.java 9. backend/src/main/java/com/leadflow/chat/controller/PublicChatController.java IMPORTANT:​- Recover from the original LeadFlow AI
     architecture/conversation wherever possible.​- Do NOT invent a different architecture.​- Do NOT redesign the AI pipeline.​- Do NOT introduce new dependencies unless the existing project already requires them.​-
     Must remain compatible with:​- Spring Boot 3.2​- Java 17​- PostgreSQL​- Flyway V1-V10​- TenantBaseEntity​- Hibernate @TenantId​- TenantContext​- JWT/AuthUser​- existing ChatService.java​- existing
     CustomerChatController.java​- existing AI FastAPI service​- Preserve existing package names and class names.​- Preserve existing API paths and DTO structures where recoverable.​- No TODOs, placeholders,
     pseudocode, or "implement later".​- If an exact original file cannot be recovered, reconstruct the smallest compatible implementation based ONLY on the already-established LeadFlow architecture and database
     migrations.​- Clearly label such files as RECONSTRUCTED rather than claiming exact recovery. ### CRITICAL COMPATIBILITY CHECKS Before outputting the files, verify:​1. BusinessKnowledge matches Flyway
     V4. 2. LeadFlow's knowledge model supports the existing ChatService call: knowledgeRepository.findByIsActiveTrue() 3. The returned knowledge entity supports: getType().name() getContent() 4. AiServiceClient
     supports the exact existing ChatService call: aiServiceClient.processMessage( content, oldScore, knowledge, rules ) 5. AiResponse provides all methods already used by ChatService: - getNewScore() - getIntent() -
     getExtractedEntities() - isRequiresHandoff() - getAction() - getResponseMessage() 6. PublicChatController must remain compatible with the existing public campaign/customer flow and anonymous customer
     session architecture. 7. Do not modify ChatService.java. 8. Do not modify Flyway migrations. ### OUTPUT FORMAT For EVERY file output: FILE: <exact path> ```<language> <complete file contents>

     Response: FILE: backend/src/main/java/com/leadflow/knowledge/dto/KnowledgeDto.java

     package com.leadflow.knowledge.dto;

     import com.leadflow.knowledge.entity.KnowledgeType;
     import lombok.Data;

     import java.util.UUID;

     @Data
     public class KnowledgeDto {
         private UUID id;
         private UUID businessId;
         private KnowledgeType type;
         private String content;
         private Boolean isActive;
     }
