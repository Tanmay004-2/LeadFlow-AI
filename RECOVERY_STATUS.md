# LeadFlow AI — Recovery Status

This repository artifact was assembled from the uploaded **LeadFlow AI Project Initialization** PDF.

- Source basis: uploaded PDF / recorded Gemini conversation
- Recovered file markers: 73
- Runtime build/test: **not performed yet**
- Files explicitly unavailable or not present as complete source in the PDF are listed below.
- No new project phase or architecture was created.

## Important
Some recovered files were explicitly labeled `RECONSTRUCTED` in the PDF. They are included because they were subsequently output as source during the recovery conversation. They should still be treated as needing compile/runtime verification.

## Known missing/unrecoverable from the PDF
- `frontend/src/app/layout.tsx`
- `frontend/src/app/page.tsx`
- `frontend/src/app/dashboard/[businessId]/page.tsx`
- `ai_service/main.py`
- `ai_service/orchestrator.py`
- `ai_service/models/interfaces.py`
- `ai_service/models/simple_models.py`
- `backend/src/main/java/com/leadflow/dashboard/dto/BusinessDashboardDto.java`
- `backend/src/main/java/com/leadflow/dashboard/dto/PlatformAdminDashboardDto.java`
- `backend/src/main/java/com/leadflow/dashboard/controller/BusinessDashboardController.java`
- `backend/src/main/java/com/leadflow/dashboard/controller/PlatformAdminController.java`
- `backend/src/main/java/com/leadflow/dashboard/service/DashboardService.java`
- `backend/src/main/java/com/leadflow/dashboard/service/PlatformAdminService.java`
- `backend/src/main/java/com/leadflow/nurture/entity/FollowUpStatus.java`
- `backend/src/main/java/com/leadflow/nurture/entity/FollowUpTask.java`
- `backend/src/main/java/com/leadflow/nurture/repository/FollowUpTaskRepository.java`
- `backend/src/main/java/com/leadflow/nurture/service/FollowUpService.java`
- `backend/src/main/java/com/leadflow/sales/entity/Appointment.java`
- `backend/src/main/java/com/leadflow/sales/entity/AssignmentSettings.java`
- `backend/src/main/java/com/leadflow/sales/service/AgentAssignmentService.java`
- `backend/src/main/java/com/leadflow/sales/service/AppointmentService.java`
- `backend/src/main/java/com/leadflow/subscription/entity/Subscription.java`
- `backend/src/main/java/com/leadflow/subscription/entity/SubscriptionStatus.java`
- `backend/src/main/java/com/leadflow/subscription/interceptor/SubscriptionInterceptor.java`
- `backend/src/main/java/com/leadflow/subscription/payment/MockPaymentProvider.java`
- `backend/src/main/java/com/leadflow/subscription/payment/PaymentGatewayProvider.java`
- `backend/src/main/java/com/leadflow/subscription/repository/SubscriptionRepository.java`
- `backend/src/main/java/com/leadflow/subscription/service/SubscriptionService.java`

Additional files may still be missing if they were only referenced in the directory tree and never emitted with a complete `FILE:` block.

## Next step
Run a real build after the recovered repository is assembled. Fix only actual compiler/test/runtime errors, then commit the verified result.
